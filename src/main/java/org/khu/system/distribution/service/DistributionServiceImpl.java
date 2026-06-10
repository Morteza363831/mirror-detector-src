package org.khu.system.distribution.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.cache.DistributionCache;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.mapper.DistributionMapper;
import org.khu.system.distribution.domain.model.Distribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<DistributionResultDto> getByActive(Boolean isActive, Integer page, Integer size) {

        Page<Distribution> distributionPage = pagedDistributions(distributionCache.distributions(), PageRequest.of(page, size));
        List<Distribution> selectedDistributions = distributionCache.distributions().stream()
                .filter(founded -> founded.getIsActive() == isActive)
                .toList();

        Page<Distribution> distributionPage = pagedDistributions(selectedDistributions, PageRequest.of(page, size));

        return distributionPage
                .map(distributionMapper::toResultDto);
    }


        return distributionPage
                .map(distributionMapper::toResultDto);
    }

    private Page<Distribution> pagedDistributions(List<Distribution> distributions, PageRequest pageRequest) {

        int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int end = Math.min((pageRequest.getPageNumber() + 1) * pageRequest.getPageSize(), distributions.size());

        if (start >= distributions.size()) {
            return new PageImpl<>(List.of(), pageRequest, distributions.size());
        }

        return new PageImpl<>(distributions.subList(start, end), pageRequest, distributions.size());
    }
}
