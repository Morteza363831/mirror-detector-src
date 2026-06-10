package org.khu.system.country.controller;

import lombok.RequiredArgsConstructor;
import org.khu.structure.ResponseBody;
import org.khu.system.country.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    // services
    private final CountryService countryService;


    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBody> getByName(@PathVariable String name) {

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .data(countryService.getByName(name))
                        .build()
                );

    }

    @GetMapping("/code2/{code2}")
    public ResponseEntity<ResponseBody> getByCode2(@PathVariable String code2) {

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .data(countryService.getByCode2(code2))
                        .build()
                );

    }

    @GetMapping("code3/{code3}")
    public ResponseEntity<ResponseBody> getByCode3(@PathVariable String code3) {

        return ResponseEntity
                .ok(ResponseBody.builder()
                        .data(countryService.getByCode3(code3))
                        .build()
                );

    }

}
