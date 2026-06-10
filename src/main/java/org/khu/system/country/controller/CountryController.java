package org.khu.system.country.controller;

import lombok.RequiredArgsConstructor;
import org.khu.structure.ResponseBody;
import org.khu.system.country.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    // services
    private final CountryService countryService;


    @GetMapping
    public ResponseEntity<ResponseBody> getByName(@RequestParam String name) {

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .data(countryService.getByName(name))
                        .build()
                );

    }

}
