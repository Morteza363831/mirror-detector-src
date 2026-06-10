package org.khu.system.distribution.cache;

import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.handler.DistributionRequestHandler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DistributionCacheImpl implements DistributionCache {

    // tools
    private final DistributionMapper distributionMapper;

    // handlers
    private final DistributionRequestHandler distributionRequestHandler;


    @Cacheable(cacheNames = "distributions")
    @Override
    public List<Distribution> distributions() {

        List<DistributionResponseDto> distributionResponseDtoList = distributionRequestHandler.fetchDistributions();

        return distributionResponseDtoList.stream()
                .map(distributionMapper::toModel)
                .toList();

    }
}
