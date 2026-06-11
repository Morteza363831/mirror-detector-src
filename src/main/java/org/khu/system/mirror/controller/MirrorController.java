package org.khu.system.mirror.controller;

import lombok.RequiredArgsConstructor;
import org.khu.system.mirror.service.MirrorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mirrors")
@RequiredArgsConstructor
public class MirrorController {

    // services
    private final MirrorService mirrorService;


}
