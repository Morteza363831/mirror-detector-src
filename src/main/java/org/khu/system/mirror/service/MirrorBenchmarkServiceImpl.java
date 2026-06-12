package org.khu.system.mirror.service;

import lombok.RequiredArgsConstructor;
import org.khu.logging.DebugLogging;
import org.khu.logging.ErrorLogging;
import org.khu.system.mirror.domain.dto.BenchmarkResult;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ErrorLogging
@DebugLogging
@Service
@RequiredArgsConstructor
public class MirrorBenchmarkServiceImpl implements MirrorBenchmarkService {

    // tools
    private final HttpClient client;


    @Override
    public MirrorBenchmarkDto benchmark(MirrorResultDto mirror) {

        BenchmarkResult benchmarkResult = BenchmarkResult.builder().build();

        try {

            HttpRequest request = makeRequest(mirror.baseUrl());

            benchmarkResult = handleResponse(request);

        }
        catch (Exception ignored) {} // todo

        double score = calculateScore
                (
                        benchmarkResult.isReachable(),
                        benchmarkResult.getResponseTimeMs(),
                        benchmarkResult.getSpeedMbps()
                );

        return new MirrorBenchmarkDto
                (
                mirror.name(),
                mirror.displayName(),
                mirror.baseUrl(),
                benchmarkResult.isReachable(),
                benchmarkResult.getStatus(),
                benchmarkResult.getConnectTime(),
                benchmarkResult.getResponseTimeMs(),
                benchmarkResult.getSpeedMbps(),
                benchmarkResult.getContentLength(),
                score
                );
    }

    private HttpRequest makeRequest(String baseUrl) {

        URI testUri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("dists", "noble", "Release")
                .build()
                .toUri();

        return HttpRequest.newBuilder()
                .uri(testUri)
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
    }

    private BenchmarkResult handleResponse(HttpRequest request) {

        // testable properties
        long connectTime = -1;
        long responseTimeMs = -1;
        double speedMbps = 0;
        long contentLength = 0;
        int status = 0;
        boolean reachable = false;
        
        
        long start = System.nanoTime();

        try {

            HttpResponse<InputStream> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofInputStream()
                    );

            // set status
            status = response.statusCode();

            // set first byte time
            responseTimeMs = calculateResponseTimeMs(start);


            if (status == 200) {

                // if status be 200, so server is reachable
                reachable = true;


                long bytesRead = 0;

                long downloadStart =
                        System.nanoTime();

                // read bytes
                bytesRead = downloadBytes(response.body(), bytesRead);

                long downloadTimeMs = calculateDownloadSpeed(downloadStart);

                // content length of downloaded file
                contentLength = bytesRead;

                // find speed
                speedMbps = calculateSpeed(downloadTimeMs, bytesRead);

            }

            connectTime = responseTimeMs;


            return BenchmarkResult.builder()
                    .responseTimeMs(responseTimeMs)
                    .connectTime(connectTime)
                    .status(status)
                    .reachable(reachable)
                    .contentLength(contentLength)
                    .speedMbps(speedMbps)
                    .build();

        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo
        }

    }

    private long calculateResponseTimeMs(long start) {

        return TimeUnit
                .NANOSECONDS
                .toMillis(System.nanoTime() - start);
    }

    private long downloadBytes(InputStream body, long bytesRead) {



        byte[] buffer = new byte[8192];

        try (InputStream in = body) {

            int read;

            while ((read = in.read(buffer)) != -1) {
                bytesRead += read;
            }

            return bytesRead;

        }
        catch (IOException e) {
            throw new RuntimeException(e); // todo
        }

    }

    private long calculateDownloadSpeed(long start) {

        return TimeUnit
                .NANOSECONDS
                .toMillis(System.nanoTime() - start);
    }

    private double calculateSpeed(long downloadTimeMs, long bytesRead) {
        return downloadTimeMs > 0 ? (bytesRead * 8.0) / (downloadTimeMs * 1000.0) : 0;
    }


    @Override
    public List<MirrorBenchmarkDto> benchmarkAll(List<MirrorResultDto> mirrors) {

        ExecutorService executor = Executors.newFixedThreadPool(20);

        try {

            List<CompletableFuture<MirrorBenchmarkDto>> futures = mirrors.stream()
                    .map(m ->
                                    CompletableFuture.supplyAsync(() -> benchmark(m), executor)
                    )
                    .toList();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .sorted(Comparator
                            .comparingDouble(MirrorBenchmarkDto::score)
                            .reversed()
                    )
                    .toList();

        } finally {
            executor.shutdown();
        }
    }

    private double calculateScore(boolean reachable, long latency, double speed) {

        if (!reachable) {
            return 0;
        }

        double latencyScore =
                latency > 0
                        ? 1000.0 / latency
                        : 0;

        return (latencyScore * 0.30) + (speed * 0.70);

    }
}