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

    @Override
    public CountryResultDto getByCode2(String code2) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode2().equalsIgnoreCase(code2))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new RuntimeException()); // todo
    }

    @Override
    public CountryResultDto getByCode3(String code3) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode3().equalsIgnoreCase(code3))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new RuntimeException()); // todo
    }
}
