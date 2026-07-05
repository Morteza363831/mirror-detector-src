package org.khu.system.mirror.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.service.CountryService;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.service.DistributionService;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.domain.mapper.MirrorMapper;
import org.khu.system.mirror.domain.model.Mirror;
import org.khu.system.mirror.handler.MirrorRequestHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.khu.system.country.mock.CountryTestFactory.iran;
import static org.khu.system.distribution.mock.DistributionTestFactory.ubuntu;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.khu.system.mirror.mock.MirrorTestFactory.*;

@ExtendWith(MockitoExtension.class)
class MirrorServiceImplTest {

    // Unit Under Test
    @InjectMocks
    private MirrorServiceImpl service;

    // Mocks
    @Mock
    private MirrorMapper mirrorMapper;

    @Mock
    private MirrorRequestHandler mirrorRequestHandler;

    @Mock
    private CountryService countryService;

    @Mock
    private DistributionService distributionService;

    @Mock
    private MirrorBenchmarkService mirrorBenchmarkService;

    private Mirror mirror1;
    private Mirror mirror2;

    private Country iran;
    private Distribution ubuntu;


    @BeforeEach
    void setup() {
        mirror1 = mirror1();
        mirror2 = mirror2();

        iran = iran();

        ubuntu = ubuntu();
    }

    @Test
    void shouldReturnMirrorsByDistributionAndCountry() {

        when(countryService.getModelByCode2(iran.getCode2())).thenReturn(iran);

        when(distributionService.getModelByName(ubuntu.getName())).thenReturn(ubuntu);

        when(mirrorRequestHandler.fetchMirrorsByDistributionAndCountry(anyString(), anyString(), anyInt()))
                .thenReturn(responseList());

        whenClauseOnMapping();

        List<MirrorResultDto> result = service.getAllByDistributionAndCountry(ubuntu.getName(), iran.getCode2(), 5);

        assertEquals(2, result.size());

        verify(countryService).getModelByCode2(iran.getCode2());
        verify(distributionService).getModelByName(ubuntu.getName());
        verify(mirrorRequestHandler).fetchMirrorsByDistributionAndCountry(anyString(), anyString(), eq(5));
    }

    @Test
    void shouldReturnEmptyListWhenCountryNotFound() {

        when(countryService.getModelByCode2(anyString())).thenReturn(null);

        List<MirrorResultDto> result = service.getAllByDistributionAndCountry(ubuntu.getName(), iran.getCode2(), 5);

        assertTrue(result.isEmpty());

        verifyNoInteractions(mirrorRequestHandler);
    }

    @Test
    void shouldReturnEmptyListWhenDistributionNotFound() {

        when(countryService.getModelByCode2(anyString())).thenReturn(iran);

        when(distributionService.getModelByName(anyString())).thenReturn(null);

        List<MirrorResultDto> result = service.getAllByDistributionAndCountry(ubuntu.getName(), iran.getCode2(), 5);

        assertTrue(result.isEmpty());

        verifyNoInteractions(mirrorRequestHandler);
    }

    @Test
    void shouldBenchmarkMirrorsByDistributionAndCountry() {

        when(countryService.getModelByCode2(anyString())).thenReturn(iran);

        when(distributionService.getModelByName(anyString())).thenReturn(ubuntu);

        when(mirrorRequestHandler.fetchMirrorsByDistributionAndCountry(anyString(), anyString(), anyInt()))
                .thenReturn(responseList());

        whenClauseOnMapping();

        when(mirrorBenchmarkService.benchmarkAll(anyList())).thenReturn(benchmarkList());

        List<MirrorBenchmarkDto> result = service.testAllByDistributionAndCountry(ubuntu.getName(), iran.getCode2(), 5);

        assertEquals(2, result.size());

        verify(mirrorBenchmarkService).benchmarkAll(anyList());
    }

    @Test
    void shouldReturnMirrorsByDistribution() {

        when(distributionService.getModelByName(anyString())).thenReturn(ubuntu);

        when(mirrorRequestHandler.fetchMirrorsByDistribution(anyString(), anyInt())).thenReturn(responseList());

        whenClauseOnMapping();

        List<MirrorResultDto> result = service.getAllByDistribution(ubuntu.getName(), 5);

        assertEquals(2, result.size());

        verify(mirrorRequestHandler).fetchMirrorsByDistribution(anyString(), eq(5));
    }

    @Test
    void shouldReturnEmptyListWhenDistributionMissing() {

        when(distributionService.getModelByName(anyString())).thenReturn(null);

        List<MirrorResultDto> result = service.getAllByDistribution(ubuntu.getName(), 5);

        assertTrue(result.isEmpty());

        verifyNoInteractions(mirrorRequestHandler);
    }

    @Test
    void shouldBenchmarkMirrorsByDistribution() {

        when(distributionService.getModelByName(anyString())).thenReturn(ubuntu);

        when(mirrorRequestHandler.fetchMirrorsByDistribution(anyString(), anyInt())).thenReturn(responseList());

        whenClauseOnMapping();

        when(mirrorBenchmarkService.benchmarkAll(anyList())).thenReturn(benchmarkList());

        List<MirrorBenchmarkDto> result = service.testAllByDistribution("ubuntu", 5);

        assertEquals(2, result.size());

        verify(mirrorBenchmarkService).benchmarkAll(anyList());
    }


    private void whenClauseOnMapping() {

        when(mirrorMapper.toResultDto(any(MirrorResponseDto.class))).thenAnswer(invocation -> {

            MirrorResponseDto responseDto = invocation.getArgument(0);

            return new MirrorResultDto(
                    responseDto.name(),
                    responseDto.displayName(),
                    responseDto.enabled(),
                    responseDto.speed(),
                    responseDto.baseUrl()
            );

        });

    }
}