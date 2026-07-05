package org.khu.system.distribution.handler;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.exception.CustomException;
import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.utils.LauncePadSettings;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistributionRequestHandlerTest {

    // Unit Under Test
    @InjectMocks
    private DistributionRequestHandler handler;


    @Mock
    private LauncePadSettings launcePadSettings;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<String> response;

    @Mock
    private JsonNode rootNode;

    @Mock
    private JsonNode entriesNode;


    @Test
    void shouldFetchDistributionsSuccessfully() throws Exception {

        JavaType listType = TypeFactory
                .defaultInstance()
                .constructCollectionType(List.class, DistributionResponseDto.class);

        List<DistributionResponseDto> expected = List.of(mock(DistributionResponseDto.class));


        when(launcePadSettings.getBaseUrl()).thenReturn("https://api.launchpad.net/devel");

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);

        when(response.body()).thenReturn("{\"entries\":[]}");

        when(objectMapper.readTree(anyString())).thenReturn(rootNode);

        when(rootNode.get("entries")).thenReturn(entriesNode);

        when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

        when(objectMapper.convertValue(eq(entriesNode), eq(listType))).thenReturn(expected);


        List<DistributionResponseDto> result = handler.fetchDistributions();


        assertEquals(expected, result);

        verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void shouldThrowWhenResponseStatusIsNot200() throws Exception {

        when(launcePadSettings.getBaseUrl()).thenReturn("https://api.launchpad.net/devel");

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(500);


        assertThrows(
                CustomException.class,
                () -> handler.fetchDistributions()
        );
    }

    @Test
    void shouldThrowWhenIOExceptionOccurs() throws Exception {

        when(launcePadSettings.getBaseUrl()).thenReturn("https://api.launchpad.net/devel");

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new IOException());


        assertThrows(
                CustomException.class,
                () -> handler.fetchDistributions()
        );
    }

    @Test
    void shouldThrowWhenInterruptedExceptionOccurs() throws Exception {

        when(launcePadSettings.getBaseUrl()).thenReturn("https://api.launchpad.net/devel");

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new InterruptedException());


        assertThrows(
                CustomException.class,
                () -> handler.fetchDistributions()
        );
    }
}