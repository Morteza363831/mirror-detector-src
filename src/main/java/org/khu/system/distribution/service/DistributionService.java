package org.khu.system.distribution.service;

import org.khu.system.distribution.domain.dto.DistributionResultDto;

public interface DistributionService {

    DistributionResultDto getByName(String name);
}
