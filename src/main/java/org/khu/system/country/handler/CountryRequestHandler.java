package org.khu.system.country.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.khu.logging.DebugLogging;
import org.khu.logging.ErrorLogging;
import org.khu.system.country.domain.dto.CountryResponseDto;
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

@ErrorLogging
@DebugLogging
@Component
@RequiredArgsConstructor
public class CountryRequestHandler {

    // tools
    private final LauncePadSettings launcePadSettings;
    private final ObjectMapper jsonMapper;
    private final HttpClient client;


    public List<CountryResponseDto> fetchCountries() {

        URI countriesUri = UriComponentsBuilder
                .fromUriString(launcePadSettings.getBaseUrl())
                .pathSegment("+countries")
                .queryParam("size", 252)
                .queryParam("ws.size", 252)
                .build()
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(countriesUri)
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

            List<CountryResponseDto> countryResponseDtoList =
                    jsonMapper.convertValue(
                            entries,
                            jsonMapper.getTypeFactory().constructCollectionType(List.class, CountryResponseDto.class)
                    );

            return countryResponseDtoList;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo
        }

    }

}
