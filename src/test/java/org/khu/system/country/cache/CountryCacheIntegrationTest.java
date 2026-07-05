package org.khu.system.country.cache;

import org.junit.jupiter.api.Test;
import org.khu.system.country.domain.dto.CountryResponseDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.khu.system.country.handler.CountryRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.khu.system.country.mock.CountryTestFactory.iranResponseDto;
import static org.mockito.Mockito.*;

@SpringBootTest
class CountryCacheIntegrationTest {

    @Autowired
    private CountryCache countryCache;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private CountryRequestHandler countryRequestHandler;

    @MockitoBean
    private CountryMapper countryMapper;


    @Test
    void shouldCacheCountries() {

        CountryResponseDto response = iranResponseDto();

        when(countryRequestHandler.fetchCountries()).thenReturn(List.of(response));

        when(countryMapper.toModel(response)).thenReturn(org.khu.system.country.mock.CountryTestFactory.iran());

        cacheManager.getCache("countries").clear();

        countryCache.countries();
        countryCache.countries();
        countryCache.countries();


        verify(countryRequestHandler, times(1)).fetchCountries();
        verify(countryMapper, times(1)).toModel(response);
    }
}