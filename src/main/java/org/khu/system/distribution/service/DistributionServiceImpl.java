package org.khu.system.distribution.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.handler.DistributionRequestHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionServiceImpl implements DistributionService {

    // tools
    private final DistributionMapper distributionMapper;

    // handlers
    private final DistributionRequestHandler distributionRequestHandler;
}
