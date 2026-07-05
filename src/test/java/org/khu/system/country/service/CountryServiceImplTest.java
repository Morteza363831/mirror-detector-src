package org.khu.system.country.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.exception.CustomException;
import org.khu.system.country.cache.CountryCache;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.khu.system.country.domain.model.Country;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.khu.system.country.mock.CountryTestFactory.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    // service under test
    @InjectMocks
    private CountryServiceImpl service;

    // mock services
    @Mock
    private CountryMapper countryMapper;

    @Mock
    private CountryCache countryCache;

    // default properties
    private Country iran;
    private Country germany;

    @BeforeEach
    void setup() {

        iran = iran();
        germany = germany();
    }


    @Test
    void shouldReturnCountryByName() {

        when(countryCache.countries()).thenReturn(List.of(iran));

        when(countryMapper.toResultDto(any(Country.class))).thenReturn(iranResultDto());

        CountryResultDto result = service.getByName(iran.getName());

        assertNotNull(result);

        assertEquals(iran.getName(), result.name());

        verify(countryCache).countries();
    }

    @Test
    void shouldReturnCountryByCode2() {

        when(countryCache.countries()).thenReturn(List.of(iran));

        when(countryMapper.toResultDto(any(Country.class))).thenReturn(iranResultDto());

        CountryResultDto result = service.getByCode2(iran.getCode2());

        assertEquals(iran.getCode2(), result.code2());
    }

    @Test
    void shouldReturnCountryByCode3() {

        when(countryCache.countries()).thenReturn(List.of(iran));

        when(countryMapper.toResultDto(any(Country.class))).thenReturn(iranResultDto());

        CountryResultDto result = service.getByCode3(iran.getCode3());

        assertEquals(iran.getCode3(), result.code3());
    }

    @Test
    void shouldThrowWhenCountryNameNotFound() {

        when(countryCache.countries()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> service.getByName("Unknown"));

        verify(countryCache, times(1)).countries();
        verifyNoInteractions(countryMapper);
    }

    @Test
    void shouldThrowWhenCountryCode2NotFound() {

        when(countryCache.countries()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> service.getByCode2("XX"));

        verify(countryCache, times(1)).countries();
        verifyNoInteractions(countryMapper);
    }

    @Test
    void shouldThrowWhenCountryCode3NotFound() {

        when(countryCache.countries()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> service.getByCode3("XXX"));

        verify(countryCache, times(1)).countries();
        verifyNoInteractions(countryMapper);
    }

    @Test
    void shouldReturnPagedCountries() {

        Integer page = 0;
        Integer size = 2;

        when(countryCache.countries()).thenReturn(List.of(iran, germany));

        when(countryMapper.toResultDto(any(Country.class))).thenAnswer(invocation -> {

                    Country country = invocation.getArgument(0);

                    return new CountryResultDto(
                            country.getName(),
                            country.getCode2(),
                            country.getCode3(),
                            country.getTitle(),
                            country.getDescription()
                    );

                });

        Page<CountryResultDto> countryResultDtoPage = service.getAll(page, size);

        assertEquals(size, countryResultDtoPage.getContent().size());

        assertEquals((long) size, countryResultDtoPage.getTotalElements());

        verify(countryCache).countries();
        verify(countryMapper, times(2)).toResultDto(any(Country.class));
    }

    @Test
    void shouldReturnModelByName() {

        when(countryCache.countries()).thenReturn(List.of(iran));

        Country result = service.getModelByName("Iran");

        assertNotNull(result);

        verify(countryCache).countries();
    }

    @Test
    void shouldReturnNullModelWhenNotFound() {

        when(countryCache.countries()).thenReturn(List.of());

        Country result = service.getModelByName("Iran");

        assertNull(result);

        verify(countryCache, times(1)).countries();
    }

    @Test
    void shouldReturnAllModels() {

        when(countryCache.countries()).thenReturn(List.of(iran, germany));

        List<Country> result = service.getModels();

        assertEquals(2, result.size());

        verify(countryCache).countries();
    }
}