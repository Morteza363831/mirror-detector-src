package org.khu.system.distribution.cache;

import org.junit.jupiter.api.Test;
import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.handler.DistributionRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.khu.system.distribution.mock.DistributionTestFactory.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DistributionCacheIntegrationTest {

    @Autowired
    private DistributionCache distributionCache;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private DistributionRequestHandler distributionRequestHandler;

    @MockitoBean
    private DistributionMapper distributionMapper;


    @Test
    void shouldCacheDistributions() {

        DistributionResponseDto response = ubuntuResponseDto();

        when(distributionRequestHandler.fetchDistributions()).thenReturn(List.of(response));

        when(distributionMapper.toModel(response)).thenReturn(ubuntu());


        cacheManager.getCache("distributions").clear();

        distributionCache.distributions();
        distributionCache.distributions();
        distributionCache.distributions();


        verify(distributionRequestHandler, times(1)).fetchDistributions();
        verify(distributionMapper, times(1)).toModel(response);
    }
}