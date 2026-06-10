package org.khu.system.distribution.controller;

import lombok.RequiredArgsConstructor;
import org.khu.system.distribution.service.DistributionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DistributionController {

    // services
    private final DistributionService distributionService;

}
