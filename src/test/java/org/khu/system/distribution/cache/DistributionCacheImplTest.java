package org.khu.system.distribution.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.handler.DistributionRequestHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.khu.system.distribution.mock.DistributionTestFactory.*;

@ExtendWith(MockitoExtension.class)
class DistributionCacheImplTest {

    // Unit Under Test
    @InjectMocks
    private DistributionCacheImpl cache;


    @Mock
    private DistributionMapper distributionMapper;

    @Mock
    private DistributionRequestHandler distributionRequestHandler;

    // default properties
    private Distribution ubuntu;

    private Distribution arch;

    private DistributionResponseDto ubuntuResponse;

    private DistributionResponseDto archResponse;

    private List<DistributionResponseDto> distributionResponseDtoList;


    @BeforeEach
    void setUp() {

        ubuntu = ubuntu();
        arch = arch();

        ubuntuResponse = ubuntuResponseDto();
        archResponse = archResponseDto();

        distributionResponseDtoList = responseDtoList();
    }


    @Test
    void shouldLoadDistributionsAndMapThem() {

        when(distributionRequestHandler.fetchDistributions()).thenReturn(distributionResponseDtoList);

        when(distributionMapper.toModel(ubuntuResponse)).thenReturn(ubuntu);

        when(distributionMapper.toModel(archResponse)).thenReturn(arch);


        List<Distribution> result = cache.distributions();


        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("ubuntu", result.get(0).getName());
        assertEquals("arch", result.get(1).getName());

        verify(distributionRequestHandler).fetchDistributions();

        verify(distributionMapper).toModel(ubuntuResponse);
        verify(distributionMapper).toModel(archResponse);

        verifyNoMoreInteractions(distributionMapper, distributionRequestHandler);
    }

    @Test
    void shouldReturnEmptyListWhenHandlerReturnsEmptyList() {

        when(distributionRequestHandler.fetchDistributions()).thenReturn(List.of());


        List<Distribution> result = cache.distributions();


        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(distributionRequestHandler).fetchDistributions();
        verifyNoInteractions(distributionMapper);
    }
}