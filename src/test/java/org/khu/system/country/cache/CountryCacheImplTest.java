package org.khu.system.country.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khu.system.country.domain.dto.CountryResponseDto;
import org.khu.system.country.domain.mapper.CountryMapper;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.handler.CountryRequestHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.khu.system.country.mock.CountryTestFactory.*;

@ExtendWith(MockitoExtension.class)
class CountryCacheImplTest {

    // Unit Under Test
    @InjectMocks
    private CountryCacheImpl cache;


    @Mock
    private CountryMapper countryMapper;

    @Mock
    private CountryRequestHandler countryRequestHandler;

    // default properties
    private Country iran;

    private Country germany;

    private CountryResponseDto iranResponse;

    private CountryResponseDto germanyResponse;

    private List<CountryResponseDto> countryResponseDtoList;


    @BeforeEach
    void setUp() {

        iran = iran();
        germany = germany();

        iranResponse = iranResponseDto();
        germanyResponse = germanyResponseDto();
        countryResponseDtoList = responseDtoList();
    }


    @Test
    void shouldLoadCountriesAndMapThem() {

        when(countryRequestHandler.fetchCountries()).thenReturn(countryResponseDtoList);

        when(countryMapper.toModel(iranResponse)).thenReturn(iran);

        when(countryMapper.toModel(germanyResponse)).thenReturn(germany);


        List<Country> result = cache.countries();


        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Iran", result.get(0).getName());
        assertEquals("Germany", result.get(1).getName());

        verify(countryRequestHandler).fetchCountries();

        verify(countryMapper).toModel(iranResponse);
        verify(countryMapper).toModel(germanyResponse);

        verifyNoMoreInteractions(countryMapper, countryRequestHandler);
    }

    @Test
    void shouldReturnEmptyListWhenHandlerReturnsEmptyList() {

        when(countryRequestHandler.fetchCountries()).thenReturn(List.of());


        List<Country> result = cache.countries();


        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(countryRequestHandler).fetchCountries();
        verifyNoInteractions(countryMapper);
    }
}