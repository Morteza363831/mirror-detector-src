package org.khu.system.country.domain.mapper;

import org.khu.system.country.domain.dto.CountryResponseDto;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring")
public interface CountryMapper {

    Country toModel(CountryResponseDto responseDto);

    CountryResultDto toResultDto(Country country);

}
