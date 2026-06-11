package org.khu.system.mirror.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.mirror.domain.mapper.MirrorMapper;
import org.khu.system.mirror.handler.MirrorRequestHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MirrorServiceImpl implements MirrorService {

    // tools
    private final MirrorMapper mirrorMapper;

    // handler
    private final MirrorRequestHandler mirrorRequestHandler;



}
