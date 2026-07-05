package org.khu.system.country.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khu.exception.ErrorResolver;
import org.khu.system.country.domain.dto.CountryResultDto;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.khu.system.country.mock.CountryTestFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountryService countryService;

    @MockitoBean
    private ErrorResolver errorResolver;

    private Country iran;

    private List<CountryResultDto> resultDtoList;

    @BeforeEach
    public void setUp() {
        iran = iran();
        resultDtoList = resultDtoList();
    }


    @Test
    void shouldGetCountryByName() throws Exception {

        when(countryService.getByName(anyString())).thenReturn(iranResultDto());

        mockMvc.perform(
                        get("/api/v1/countries/name/Iran")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(iran.getName()))
                .andExpect(jsonPath("$.data.code2").value(iran.getCode2()))
                .andExpect(jsonPath("$.data.code3").value(iran.getCode3()));

        verify(countryService).getByName("Iran");
        verifyNoMoreInteractions(countryService);
    }


    @Test
    void shouldGetCountryByCode2() throws Exception {

        when(countryService.getByCode2(anyString())).thenReturn(iranResultDto());

        mockMvc.perform(
                        get("/api/v1/countries/code2/IR")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(iran.getName()))
                .andExpect(jsonPath("$.data.code2").value(iran.getCode2()))
                .andExpect(jsonPath("$.data.code3").value(iran.getCode3()));

        verify(countryService).getByCode2("IR");
        verifyNoMoreInteractions(countryService);
    }


    @Test
    void shouldGetCountryByCode3() throws Exception {

        when(countryService.getByCode3(anyString())).thenReturn(iranResultDto());

        mockMvc.perform(
                        get("/api/v1/countries/code3/IRN")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(iran.getName()))
                .andExpect(jsonPath("$.data.code2").value(iran.getCode2()))
                .andExpect(jsonPath("$.data.code3").value(iran.getCode3()));

        verify(countryService).getByCode3("IRN");
        verifyNoMoreInteractions(countryService);
    }


    @Test
    void shouldReturnAllCountriesWithDefaultPagination() throws Exception {

        Integer page = 0;
        Integer size = 50;

        when(countryService.getAll(anyInt(), anyInt())).thenReturn(new PageImpl<>(resultDtoList));

        mockMvc.perform(
                        get("/api/v1/countries")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(resultDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(resultDtoList.size()));

        verify(countryService).getAll(0, 50);
        verifyNoMoreInteractions(countryService);
    }


    @Test
    void shouldReturnAllCountriesWithCustomPagination() throws Exception {

        Integer page = 0;
        Integer size = 10;
        when(countryService.getAll(anyInt(), anyInt())).thenReturn(new PageImpl<>(resultDtoList));

        mockMvc.perform(
                        get("/api/v1/countries")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.data.length()").value(resultDtoList.size()));

        verify(countryService).getAll(0, 10);
        verifyNoMoreInteractions(countryService);
    }

}