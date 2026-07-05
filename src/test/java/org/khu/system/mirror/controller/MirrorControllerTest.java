package org.khu.system.mirror.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khu.exception.ErrorResolver;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.service.MirrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.khu.system.mirror.mock.MirrorTestFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MirrorController.class)
class MirrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MirrorService mirrorService;

    @MockitoBean
    private ErrorResolver errorResolver;

    // default properties
    private MirrorResultDto mirrorResultDto;

    private MirrorBenchmarkDto benchmarkDto;

    private List<MirrorResultDto> mirrorResultDtoList;

    private List<MirrorBenchmarkDto> benchmarkDtoList;


    @BeforeEach
    void setUp() {

        mirrorResultDto = resultDto1();
        benchmarkDto = benchmarkDto1();

        mirrorResultDtoList = resultList();
        benchmarkDtoList = benchmarkList();
    }


    @Test
    void shouldGetMirrorsByDistributionAndCountry() throws Exception {

        Integer size = 50;

        when(mirrorService.getAllByDistributionAndCountry(anyString(), anyString(), anyInt())).thenReturn(mirrorResultDtoList);

        mockMvc.perform(
                        get("/api/v1/mirrors/dist/ubuntu")
                                .param("code2", "ir")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(mirrorResultDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(mirrorResultDtoList.size()))
                .andExpect(jsonPath("$.data[0].name").value(mirrorResultDto.name()));

        verify(mirrorService).getAllByDistributionAndCountry("ubuntu", "ir", size);
        verifyNoMoreInteractions(mirrorService);
    }

    @Test
    void shouldBenchmarkMirrorsByDistributionAndCountry() throws Exception {

        Integer size = 50;

        when(mirrorService.testAllByDistributionAndCountry(anyString(), anyString(), anyInt())).thenReturn(benchmarkDtoList);

        mockMvc.perform(
                        get("/api/v1/mirrors/dist/ubuntu/test")
                                .param("code2", "ir")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(benchmarkDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(benchmarkDtoList.size()))
                .andExpect(jsonPath("$.data[0].name").value(benchmarkDto.name()));

        verify(mirrorService).testAllByDistributionAndCountry("ubuntu", "ir", size);
        verifyNoMoreInteractions(mirrorService);
    }

    @Test
    void shouldGetBestMirrorsByDistribution() throws Exception {

        Integer size = 50;

        when(mirrorService.getAllByDistribution(anyString(), anyInt())).thenReturn(mirrorResultDtoList);

        mockMvc.perform(
                        get("/api/v1/mirrors/best/dist/ubuntu")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(mirrorResultDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(mirrorResultDtoList.size()))
                .andExpect(jsonPath("$.data[0].name").value(mirrorResultDto.name()));

        verify(mirrorService).getAllByDistribution("ubuntu", size);
        verifyNoMoreInteractions(mirrorService);
    }

    @Test
    void shouldBenchmarkBestMirrorsByDistribution() throws Exception {

        Integer size = 50;

        when(mirrorService.testAllByDistribution(anyString(), anyInt())).thenReturn(benchmarkDtoList);

        mockMvc.perform(
                        get("/api/v1/mirrors/best/dist/ubuntu/test")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.totalElements").value(benchmarkDtoList.size()))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.data.length()").value(benchmarkDtoList.size()))
                .andExpect(jsonPath("$.data[0].name").value(benchmarkDto.name()));

        verify(mirrorService).testAllByDistribution("ubuntu", size);
        verifyNoMoreInteractions(mirrorService);
    }

}