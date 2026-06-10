package org.khu.system.distribution.domain.mapper;

import org.khu.system.distribution.domain.dto.DistributionResponseDto;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.model.Distribution;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DistributionMapper {

    Distribution toModel(DistributionResponseDto dto);

    DistributionResultDto toResultDto(Distribution distribution);
}