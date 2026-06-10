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

}
