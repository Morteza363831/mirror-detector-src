package org.khu.system.country.cache;

import lombok.RequiredArgsConstructor;
import org.khu.system.country.domain.dto.CountryResponseDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.handler.CountryRequestHandler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CountryCacheImpl implements CountryCache {

    // tools
    private final CountryMapper countryMapper;

    // handlers
    private final CountryRequestHandler countryRequestHandler;


    @Cacheable(cacheNames = "countries")
    @Override
    public List<Country> countries() {

        List<CountryResponseDto> countryResponseDtoList = countryRequestHandler.fetchCountries();

        return countryResponseDtoList.stream()
                .map(countryMapper::toModel)
                .toList();

    }
}
