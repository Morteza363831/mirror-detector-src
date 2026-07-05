package org.khu.system.mirror.handler;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.exception.CustomException;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
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
class MirrorRequestHandlerTest {

    // Unit Under Test
    @InjectMocks
    private MirrorRequestHandler handler;


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
    void shouldFetchMirrorsByDistributionAndCountrySuccessfully() throws Exception {

        JavaType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, MirrorResponseDto.class);

        List<MirrorResponseDto> expected = List.of(mock(MirrorResponseDto.class));


        when(launcePadSettings.getBaseUrl()).thenReturn("https://api.launchpad.net/devel");
        when(launcePadSettings.getMirrorWsOp()).thenReturn("getPublishedBinaries");
        when(launcePadSettings.getMirrorType()).thenReturn("archive");

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\"entries\":[]}");

        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        when(rootNode.isNull()).thenReturn(false);
        when(rootNode.isEmpty()).thenReturn(false);
        when(rootNode.get("entries")).thenReturn(entriesNode);

        when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());
        when(objectMapper.convertValue(eq(entriesNode), eq(listType))).thenReturn(expected);


        List<MirrorResponseDto> result =
                handler.fetchMirrorsByDistributionAndCountry(
                        "ubuntu",
                        "encoded-country",
                        50
                );


        assertEquals(expected, result);

        verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void shouldFetchMirrorsByDistributionSuccessfully() throws Exception {

        JavaType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, MirrorResponseDto.class);

        List<MirrorResponseDto> expected = List.of(mock(MirrorResponseDto.class));


        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\"entries\":[]}");

        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        when(rootNode.isNull()).thenReturn(false);
        when(rootNode.isEmpty()).thenReturn(false);
        when(rootNode.get("entries")).thenReturn(entriesNode);

        when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());
        when(objectMapper.convertValue(eq(entriesNode), eq(listType))).thenReturn(expected);


        List<MirrorResponseDto> result = handler.fetchMirrorsByDistribution("https://mirror.archive", 50);


        assertEquals(expected, result);

        verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void shouldReturnEmptyListWhenBodyIsEmpty() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{}");

        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        when(rootNode.isNull()).thenReturn(false);
        when(rootNode.isEmpty()).thenReturn(true);


        List<MirrorResponseDto> result =
                handler.fetchMirrorsByDistribution(
                        "https://mirror.archive",
                        50
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowWhenResponseStatusIsNot200() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(500);


        assertThrows(
                CustomException.class,
                () -> handler.fetchMirrorsByDistribution(
                        "https://mirror.archive",
                        50
                )
        );
    }

    @Test
    void shouldThrowWhenIOExceptionOccurs() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new IOException());


        assertThrows(
                CustomException.class,
                () -> handler.fetchMirrorsByDistribution(
                        "https://mirror.archive",
                        50
                )
        );
    }

    @Test
    void shouldThrowWhenInterruptedExceptionOccurs() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new InterruptedException());


        assertThrows(
                CustomException.class,
                () -> handler.fetchMirrorsByDistribution(
                        "https://mirror.archive",
                        50
                )
        );
    }
}