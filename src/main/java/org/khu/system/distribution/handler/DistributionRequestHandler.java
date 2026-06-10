package org.khu.system.distribution.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.domain.dto.DistributionResponseDto;
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
public class DistributionRequestHandler {

    // tools
    private final LauncePadSettings launcePadSettings;
    private final ObjectMapper jsonMapper;
    private final HttpClient client;


    public List<DistributionResponseDto> fetchDistributions() {

        URI distributionUri = UriComponentsBuilder
                .fromUriString(launcePadSettings.getBaseUrl())
                .pathSegment("distros")
                .build()
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(distributionUri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new RuntimeException(); // todo
            }

            JsonNode body = jsonMapper.readTree(response.body());

            if (body.isNull() || body.isEmpty()) {
                return new ArrayList<>();
            }

            JsonNode entries = body.get("entries");

            List<DistributionResponseDto> distributionResponseDtoList =
                    jsonMapper.convertValue(
                            entries,
                            jsonMapper.getTypeFactory().constructCollectionType(List.class, DistributionResponseDto.class)
                    );

            return distributionResponseDtoList;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo
        }

    }

}
