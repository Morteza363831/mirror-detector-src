package org.khu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GlobalConfigTest {

    private final GlobalConfig config = new GlobalConfig();

    @Test
    void shouldCreateObjectMapper() {

        ObjectMapper mapper = config.objectMapper();

        assertNotNull(mapper);

        assertFalse(
                mapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        );
    }

    @Test
    void shouldSerializeJavaTime() throws Exception {

        ObjectMapper mapper = config.objectMapper();

        String json = mapper.writeValueAsString(OffsetDateTime.now());

        assertNotNull(json);
        assertTrue(json.startsWith("\""));
    }

    @Test
    void shouldCreateHttpClient() {

        HttpClient client = config.httpClient();

        assertNotNull(client);
    }
}