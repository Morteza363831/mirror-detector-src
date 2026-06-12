package org.khu.system.mirror.service;

import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;

import java.util.List;

public interface MirrorBenchmarkService {

    MirrorBenchmarkDto benchmark(MirrorResultDto mirror);

    List<MirrorBenchmarkDto> benchmarkAll(
            List<MirrorResultDto> mirrors
    );

}