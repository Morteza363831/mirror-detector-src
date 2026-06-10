package org.khu.system.country.service;

import org.khu.system.country.domain.dto.CountryResultDto;

public interface CountryService {

    CountryResultDto getByName(String name);

}
