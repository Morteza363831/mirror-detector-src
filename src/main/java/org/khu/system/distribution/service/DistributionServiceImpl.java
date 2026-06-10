package org.khu.system.distribution.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.cache.DistributionCache;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionServiceImpl implements DistributionService {

    // tools
    private final DistributionMapper distributionMapper;

    // handlers
    private final DistributionCache distributionCache;


    @Override
    public DistributionResultDto getByName(String name) {

        return distributionCache.distributions()
                .stream()
                .filter(founded -> founded.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(distributionMapper::toResultDto)
                .orElseThrow(() -> new RuntimeException()); // todo

    }
}
