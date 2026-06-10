package org.khu.system.country.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.country.cache.CountryCache;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    // tools
    private final CountryMapper countryMapper;

    // cache
    private final CountryCache countryCache;


    @Override
    public CountryResultDto getByName(String name) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new RuntimeException()); // todo

    }
}
