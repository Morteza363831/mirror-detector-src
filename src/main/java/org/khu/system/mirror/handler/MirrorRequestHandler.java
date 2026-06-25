package org.khu.system.mirror.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.khu.exception.CustomException;
import org.khu.exception.ErrorCode;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.utils.DomainNames;
import org.khu.utils.LauncePadSettings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MirrorRequestHandler {

    // tools
    private final LauncePadSettings launcePadSettings;
    private final ObjectMapper jsonMapper;
    private final HttpClient client;


    public List<MirrorResponseDto> fetchMirrorsByDistributionAndCountry(String distribution, String countryEncodedLink, Integer size) {

        URI mirrorUri = UriComponentsBuilder
                .fromUriString(launcePadSettings.getBaseUrl())
                .pathSegment(distribution)
                .queryParam("ws.op", launcePadSettings.getMirrorWsOp())
                .queryParam("country", countryEncodedLink)
                .queryParam("mirror_type", launcePadSettings.getMirrorType())
                .queryParam("ws.size", size)
                .build(true)
                .toUri();

        return handleRequest(mirrorUri);

    }

    public List<MirrorResponseDto> fetchMirrorsByDistribution(String archiveMirrorsLink, Integer size) {

        URI archiveMirrorUri = UriComponentsBuilder
                .fromUriString(archiveMirrorsLink)
                .queryParam("ws.size", size)
                .build(true)
                .toUri();


        return handleRequest(archiveMirrorUri);

    }

    private List<MirrorResponseDto> handleRequest(URI uri) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new CustomException(ErrorCode.INVALID_RESPONSE, DomainNames.MIRROR.getName());
            }

            JsonNode body = jsonMapper.readTree(response.body());

            if (body.isNull() || body.isEmpty()) {
                return new ArrayList<>();
            }

            JsonNode entries = body.get("entries");

            List<MirrorResponseDto> mirrorResponseDtoList =
                    jsonMapper.convertValue(
                            entries,
                            jsonMapper.getTypeFactory().constructCollectionType(List.class, MirrorResponseDto.class)
                    );

            return mirrorResponseDtoList;

        } catch (IOException | InterruptedException e) {
            throw new CustomException(ErrorCode.COULD_NOT_PROCESS_RESPONSE, DomainNames.MIRROR.getName());
        }

    }

}
