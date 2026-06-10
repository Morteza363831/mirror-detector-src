package org.khu.system.country.service;

import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.model.Country;
import org.springframework.data.domain.Page;


public interface CountryService {

    CountryResultDto getByName(String name);

    CountryResultDto getByCode2(String code2);

    CountryResultDto getByCode3(String code3);

    Page<CountryResultDto> getAll(Integer page, Integer size);


    Country getModelByName(String name);

    Country getModelByCode2(String code2);

    Country getModelByCode3(String code3);

}
