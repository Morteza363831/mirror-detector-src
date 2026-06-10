package org.khu.system.distribution.service;

import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.springframework.data.domain.Page;


public interface DistributionService {

    DistributionResultDto getByName(String name);

    Page<DistributionResultDto> getByActive(Boolean isActive, Integer page, Integer size);

    Page<DistributionResultDto> getByPrivate(Boolean isPrivate, Integer page, Integer size);

    Page<DistributionResultDto> getAll(Integer page, Integer size);
}
