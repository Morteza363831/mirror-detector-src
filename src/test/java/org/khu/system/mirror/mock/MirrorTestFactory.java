package org.khu.system.mirror.mock;

import org.khu.system.country.domain.model.Country;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.mirror.domain.dto.BenchmarkResult;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.domain.model.Mirror;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class MirrorTestFactory {

    private MirrorTestFactory() {
    }

    /**
     * Mirror
     */

    public static Mirror mirror1() {

        return Mirror.builder()
                .name("mirror-1")
                .displayName("Tehran Mirror")
                .selfLink("https://launchpad.net/mirror")
                .enabled(true)
                .speed("100 Mbps")
                .baseUrl("https://mirror.test/")
                .build();
    }

    public static Mirror mirror2() {

        return Mirror.builder()
                .name("mirror-2")
                .displayName("Tabriz Mirror")
                .selfLink("https://launchpad.net/mirror")
                .enabled(false)
                .speed("50 Mbps")
                .baseUrl("https://mirror.test/")
                .build();
    }

    public static MirrorResponseDto responseDto1() {

        return new MirrorResponseDto(
                "mirror-1",
                "Tehran Mirror",
                "https://launchpad.net/mirror",
                true,
                "100 Mbps",
                "https://mirror.test/"
        );
    }

    public static MirrorResponseDto responseDto2() {

        return new MirrorResponseDto(
                "mirror-2",
                "Tabriz Mirror",
                "https://launchpad.net/mirror",
                false,
                "50 Mbps",
                "https://mirror.test/"
        );
    }

    public static MirrorResultDto resultDto1() {

        return new MirrorResultDto(
                "mirror-1",
                "Tehran Mirror",
                true,
                "100 Mbps",
                "https://mirror.test/"
        );
    }

    public static MirrorResultDto resultDto2() {

        return new MirrorResultDto(
                "mirror-2",
                "Tabriz Mirror",
                false,
                "50 Mbps",
                "https://mirror.test/"
        );
    }

    public static MirrorBenchmarkDto benchmarkDto1() {

        return MirrorBenchmarkDto.builder()
                .name("mirror-1")
                .displayName("Tehran Mirror")
                .baseUrl("https://mirror.test/")
                .reachable(true)
                .httpStatus(200)
                .connectTimeMs(20)
                .responseTimeMs(45)
                .downloadSpeedMbps(95)
                .contentLength(2048)
                .score(99.5)
                .build();
    }

    public static MirrorBenchmarkDto benchmarkDto2() {

        return MirrorBenchmarkDto.builder()
                .name("mirror-2")
                .displayName("Tabriz Mirror")
                .baseUrl("https://mirror.test/")
                .reachable(false)
                .httpStatus(400)
                .connectTimeMs(20)
                .responseTimeMs(45)
                .downloadSpeedMbps(95)
                .contentLength(2048)
                .score(99.5)
                .build();
    }

    public static BenchmarkResult benchmarkResult() {

        return BenchmarkResult.builder()
                .reachable(true)
                .status(200)
                .connectTime(20)
                .responseTimeMs(45)
                .speedMbps(95)
                .contentLength(2048)
                .build();
    }

    public static List<MirrorResponseDto> responseList() {
        return List.of(responseDto1(), responseDto2());
    }

    public static List<MirrorResultDto> resultList() {
        return List.of(resultDto1(), resultDto2());
    }

    public static List<MirrorBenchmarkDto> benchmarkList() {
        return List.of(benchmarkDto1(), benchmarkDto2());
    }


    public static InputStream releaseFile() {

        return new ByteArrayInputStream(
                "Ubuntu Release".getBytes(StandardCharsets.UTF_8)
        );
    }

}