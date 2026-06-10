package org.khu.system.distribution.controller;

import lombok.RequiredArgsConstructor;
import org.khu.structure.ResponseBody;
import org.khu.system.distribution.domain.dto.DistributionResultDto;
import org.khu.system.distribution.service.DistributionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/distributions")
@RequiredArgsConstructor
public class DistributionController {

    // services
    private final DistributionService distributionService;


    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBody> getByName(@PathVariable String name) {

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .data(distributionService.getByName(name))
                        .build()
                );

    }

    @GetMapping("/active")
    public ResponseEntity<ResponseBody> getByActive(@RequestParam Boolean isActive,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "50") Integer size) {

        Page<DistributionResultDto> distributionResultDtoPage = distributionService.getByActive(isActive, page, size);

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .page(page)
                        .size(size)
                        .totalElements(distributionResultDtoPage.getTotalElements())
                        .totalPages(distributionResultDtoPage.getTotalPages())
                        .data(distributionResultDtoPage.getContent())
                        .build()
                );

    }

    @GetMapping("/private")
    public ResponseEntity<ResponseBody> getByPrivate(@RequestParam Boolean isPrivate,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "50") Integer size) {

        Page<DistributionResultDto> distributionResultDtoPage = distributionService.getByPrivate(isPrivate, page, size);

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .page(page)
                        .size(size)
                        .totalElements(distributionResultDtoPage.getTotalElements())
                        .totalPages(distributionResultDtoPage.getTotalPages())
                        .data(distributionResultDtoPage.getContent())
                        .build()
                );

    }
}
