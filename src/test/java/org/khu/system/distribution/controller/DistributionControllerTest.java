package org.khu.system.distribution.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khu.exception.ErrorResolver;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.khu.system.distribution.mock.DistributionTestFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DistributionController.class)
class DistributionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DistributionService distributionService;

    @MockitoBean
    private ErrorResolver errorResolver;


    private Distribution ubuntu;

    private Distribution arch;

    private List<DistributionResultDto> resultDtoList;


    @BeforeEach
    void setUp() {

        ubuntu = ubuntu();
        arch = arch();

        resultDtoList = resultDtoList();
    }

    @Test
    void shouldGetDistributionByName() throws Exception {

        when(distributionService.getByName(anyString())).thenReturn(ubuntuResultDto());

        mockMvc.perform(
                        get("/api/v1/distributions/name/ubuntu")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(ubuntu.getName()))
                .andExpect(jsonPath("$.data.displayName").exists())
                .andExpect(jsonPath("$.data.logoLink").exists())
                .andExpect(jsonPath("$.data.active").value(ubuntu.getIsActive()))
                .andExpect(jsonPath("$.data.isPrivate").exists());

        verify(distributionService).getByName("ubuntu");
        verifyNoMoreInteractions(distributionService);
    }

    @Test
    void shouldReturnActiveDistributions() throws Exception {

        Integer page = 0;
        Integer size = 50;

        when(distributionService.getByActive(anyBoolean(), anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(ubuntuResultDto())));

        mockMvc.perform(
                        get("/api/v1/distributions/active")
                                .param("isActive", "true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value(ubuntu.getName()));

        verify(distributionService).getByActive(true, page, size);
        verifyNoMoreInteractions(distributionService);
    }

    @Test
    void shouldReturnPrivateDistributions() throws Exception {

        Integer page = 0;
        Integer size = 50;

        when(distributionService.getByPrivate(anyBoolean(), anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(archResultDto())));

        mockMvc.perform(
                        get("/api/v1/distributions/private")
                                .param("isPrivate", "true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value(arch.getName()));

        verify(distributionService).getByPrivate(true, page, size);
        verifyNoMoreInteractions(distributionService);
    }

    @Test
    void shouldReturnAllDistributionsWithDefaultPagination() throws Exception {

        Integer page = 0;
        Integer size = 50;

        when(distributionService.getAll(anyInt(), anyInt())).thenReturn(new PageImpl<>(resultDtoList));

        mockMvc.perform(
                        get("/api/v1/distributions")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(resultDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(resultDtoList.size()));

        verify(distributionService).getAll(page, size);
        verifyNoMoreInteractions(distributionService);
    }

    @Test
    void shouldReturnAllDistributionsWithCustomPagination() throws Exception {

        Integer page = 1;
        Integer size = 10;

        when(distributionService.getAll(anyInt(), anyInt())).thenReturn(new PageImpl<>(resultDtoList));

        mockMvc.perform(
                        get("/api/v1/distributions")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.data.length()").value(resultDtoList.size()));

        verify(distributionService).getAll(page, size);
        verifyNoMoreInteractions(distributionService);
    }

}