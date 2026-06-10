package org.khu.system.distribution.controller;

import lombok.RequiredArgsConstructor;
import org.khu.structure.ResponseBody;
import org.khu.system.distribution.service.DistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
