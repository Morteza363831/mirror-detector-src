package org.khu.system.mirror.controller;

import lombok.RequiredArgsConstructor;
import org.khu.structure.ResponseBody;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.service.MirrorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mirrors")
@RequiredArgsConstructor
public class MirrorController {

    // services
    private final MirrorService mirrorService;


    @GetMapping("/distribution/{distro}")
    public ResponseEntity<ResponseBody> getMirrorsByDistributionAndCountry(@PathVariable String distro,
                                                                           @RequestParam(required = false, defaultValue = "ir") String code2,
                                                                           @RequestParam(required = false, defaultValue = "50") Integer size) {

        List<MirrorResultDto> resultDtoList = mirrorService.getAllByDistributionAndCountry(distro, code2, size);

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .page(0)
                        .size(size)
                        .totalElements((long) resultDtoList.size())
                        .totalPages(1)
                        .data(resultDtoList)
                        .build()
                );
    }

    @GetMapping("/distribution/{distro}/test")
    public ResponseEntity<ResponseBody> testMirrorsByDistributionAndCountry(@PathVariable String distro,
                                                                           @RequestParam(required = false, defaultValue = "ir") String code2,
                                                                           @RequestParam(required = false, defaultValue = "50") Integer size) {

        List<MirrorBenchmarkDto> resultDtoList = mirrorService.testAllByDistributionAndCountry(distro, code2, size);

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .page(0)
                        .size(size)
                        .totalElements((long) resultDtoList.size())
                        .totalPages(1)
                        .data(resultDtoList)
                        .build()
                );
    }
}
