package org.khu.system.mirror.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.khu.system.mirror.mock.MirrorTestFactory.*;

@ExtendWith(MockitoExtension.class)
class MirrorBenchmarkServiceImplTest {

    // Unit Under Test
    @InjectMocks
    private MirrorBenchmarkServiceImpl service;

    // Mocks
    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<InputStream> response;


    @Test
    void shouldBenchmarkMirrorSuccessfully() throws Exception {

        MirrorResultDto mirror = resultDto1();

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);

        when(response.body()).thenReturn(releaseFile());

        MirrorBenchmarkDto result = service.benchmark(mirror);

        assertNotNull(result);

        assertEquals(mirror.name(), result.name());
        assertEquals(mirror.displayName(), result.displayName());
        assertEquals(mirror.baseUrl(), result.baseUrl());

        assertTrue(result.reachable());
        assertEquals(200, result.httpStatus());

        assertTrue(result.connectTimeMs() >= 0);
        assertTrue(result.responseTimeMs() >= 0);
        assertTrue(result.contentLength() > 0);
        assertTrue(result.downloadSpeedMbps() >= 0);
        assertTrue(result.score() >= 0);

        verify(client, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }


    @Test
    void shouldReturnUnreachableMirrorWhenStatusIs404() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(404);

        MirrorBenchmarkDto result = service.benchmark(resultDto1());

        assertNotNull(result);

        assertFalse(result.reachable());

        assertEquals(404, result.httpStatus());

        assertEquals(0.0, result.score());

        verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }


    // Need updates in service (Anti Pattern)
    @Test
    void shouldReturnUnreachableMirrorWhenIOExceptionOccurs() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new IOException());

        MirrorBenchmarkDto result = service.benchmark(resultDto1());

        assertNotNull(result);

        assertFalse(result.reachable());

        assertEquals(0, result.httpStatus());

        assertEquals(0.0, result.score());

        verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }


    @Test
    void shouldBenchmarkAllMirrors() throws Exception {

        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        when(response.statusCode()).thenReturn(200);

        when(response.body()).thenReturn(releaseFile());

        List<MirrorBenchmarkDto> result = service.benchmarkAll(resultList());

        assertNotNull(result);

        assertEquals(2, result.size());

        assertTrue(result.get(0).reachable());
        assertTrue(result.get(1).reachable());

        verify(client, times(2)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }


    @Test
    void shouldReturnEmptyBenchmarkList() {

        List<MirrorBenchmarkDto> result = service.benchmarkAll(List.of());

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verifyNoInteractions(client);
    }

}