package org.khu.system.country.controller;

import lombok.RequiredArgsConstructor;
import org.khu.system.country.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    // services
    private final CountryService countryService;


}
