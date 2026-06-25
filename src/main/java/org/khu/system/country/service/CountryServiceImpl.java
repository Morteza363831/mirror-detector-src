package org.khu.system.country.service;

import lombok.RequiredArgsConstructor;
import org.khu.exception.CustomException;
import org.khu.exception.ErrorCode;
import org.khu.logging.DebugLogging;
import org.khu.logging.ErrorLogging;
import org.khu.system.country.cache.CountryCache;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.khu.system.country.domain.model.Country;
import org.khu.utils.DomainNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@ErrorLogging
@DebugLogging
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    // tools
    private final CountryMapper countryMapper;

    // cache
    private final CountryCache countryCache;


    /// Client scope functionalities

    @Override
    public CountryResultDto getByName(String name) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new CustomException(ErrorCode.MODEL_NOT_FOUND, name)); // todo

    }

    @Override
    public CountryResultDto getByCode2(String code2) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode2().equalsIgnoreCase(code2))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new CustomException(ErrorCode.MODEL_NOT_FOUND, code2)); // todo
    }

    @Override
    public CountryResultDto getByCode3(String code3) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode3().equalsIgnoreCase(code3))
                .findFirst()
                .map(countryMapper::toResultDto)
                .orElseThrow(() -> new CustomException(ErrorCode.MODEL_NOT_FOUND, code3)); // todo
    }

    @Override
    public Page<CountryResultDto> getAll(Integer page, Integer size) {

        Page<Country> countryPage = pagedCountries(countryCache.countries(), PageRequest.of(page, size));

        return countryPage
                .map(countryMapper::toResultDto);

    }


    private Page<Country> pagedCountries(List<Country> countries, PageRequest pageRequest) {

        int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int end = Math.min((pageRequest.getPageNumber() + 1) * pageRequest.getPageSize(), countries.size());

        if (start >= countries.size()) {
            return new PageImpl<>(List.of(), pageRequest, countries.size());
        }

        return new PageImpl<>(countries.subList(start, end), pageRequest, countries.size());
    }


    /// Service scope functionalities

    @Override
    public Country getModelByName(String name) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Country getModelByCode2(String code2) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode2().equalsIgnoreCase(code2))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Country getModelByCode3(String code3) {

        return countryCache.countries()
                .stream()
                .filter(founded -> founded.getCode3().equalsIgnoreCase(code3))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Country> getModels() {
        return countryCache.countries();
    }

}
