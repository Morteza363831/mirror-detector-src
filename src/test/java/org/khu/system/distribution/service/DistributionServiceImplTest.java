package org.khu.system.distribution.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.exception.CustomException;
import org.khu.system.distribution.cache.DistributionCache;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.domain.model.Distribution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.khu.system.distribution.mock.DistributionTestFactory.*;

@ExtendWith(MockitoExtension.class)
class DistributionServiceImplTest {

    // Unit Under Test
    @InjectMocks
    private DistributionServiceImpl service;

    // Mocks
    @Mock
    private DistributionMapper distributionMapper;

    @Mock
    private DistributionCache distributionCache;

    // Default properties
    private Distribution ubuntu;
    private Distribution arch;

    @BeforeEach
    void setup() {

        ubuntu = ubuntu();
        arch = arch();
    }


    @Test
    void shouldReturnDistributionByName() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu));

        when(distributionMapper.toResultDto(any(Distribution.class))).thenReturn(ubuntuResultDto());

        DistributionResultDto result = service.getByName(ubuntu.getName());

        assertNotNull(result);
        assertEquals(ubuntu.getName(), result.name());

        verify(distributionCache).distributions();
        verify(distributionMapper).toResultDto(ubuntu);
    }

    @Test
    void shouldThrowWhenDistributionNotFound() {

        when(distributionCache.distributions()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> service.getByName("fedora"));

        verify(distributionCache).distributions();
        verifyNoInteractions(distributionMapper);
    }

    @Test
    void shouldReturnActiveDistributions() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu, arch));

        when(distributionMapper.toResultDto(any(Distribution.class))).thenAnswer(invocation -> {

                    Distribution distribution = invocation.getArgument(0);

                    return new DistributionResultDto(
                            distribution.getLogoLink(),
                            distribution.getName(),
                            distribution.getDisplayName(),
                            distribution.getIsActive(),
                            distribution.getIsPrivate(),
                            distribution.getCdImageMirrorsCollectionLink()
                    );

                });

        Page<DistributionResultDto> page = service.getByActive(true, 0, 10);

        assertEquals(1, page.getContent().size()
        );

        assertTrue(page.getContent().get(0).active());

        verify(distributionMapper, times(1)).toResultDto(any(Distribution.class));
    }

    @Test
    void shouldReturnPrivateDistributions() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu, arch));

        when(distributionMapper.toResultDto(any(Distribution.class))).thenAnswer(invocation -> {

                    Distribution distribution = invocation.getArgument(0);

                    return new DistributionResultDto(
                            distribution.getLogoLink(),
                            distribution.getName(),
                            distribution.getDisplayName(),
                            distribution.getIsActive(),
                            distribution.getIsPrivate(),
                            distribution.getCdImageMirrorsCollectionLink()
                    );

                });

        Page<DistributionResultDto> page = service.getByPrivate(true, 0, 10);

        assertEquals(1, page.getContent().size());

        assertTrue(page.getContent().get(0).isPrivate());

        verify(distributionMapper, times(1)).toResultDto(any(Distribution.class));
    }

    @Test
    void shouldReturnAllDistributions() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu, arch));

        when(distributionMapper.toResultDto(any(Distribution.class))).thenAnswer(invocation -> {

                    Distribution distribution = invocation.getArgument(0);

                    return new DistributionResultDto(
                            distribution.getLogoLink(),
                            distribution.getName(),
                            distribution.getDisplayName(),
                            distribution.getIsActive(),
                            distribution.getIsPrivate(),
                            distribution.getCdImageMirrorsCollectionLink()
                    );

                });

        Page<DistributionResultDto> page = service.getAll(0, 10);

        assertEquals(2, page.getTotalElements());

        assertEquals(2, page.getContent().size());

        verify(distributionMapper, times(2)).toResultDto(any(Distribution.class));
    }

    @Test
    void shouldReturnModelByName() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu));

        Distribution result = service.getModelByName("ubuntu");

        assertNotNull(result);

        verify(distributionCache).distributions();
    }

    @Test
    void shouldReturnNullModelWhenDistributionNotFound() {

        when(distributionCache.distributions()).thenReturn(List.of());

        Distribution result = service.getModelByName("ubuntu");

        assertNull(result);

        verify(distributionCache).distributions();
    }

    @Test
    void shouldReturnAllModels() {

        when(distributionCache.distributions()).thenReturn(List.of(ubuntu, arch));

        List<Distribution> result = service.getModels();

        assertEquals(2, result.size());

        verify(distributionCache).distributions();
    }

}