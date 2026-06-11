package org.khu.system.mirror.service;

import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;

import java.util.List;

public interface MirrorService {

    List<MirrorResultDto> getAllByDistributionAndCountry(String distribution, String code2, Integer size);

    List<MirrorBenchmarkDto> testAllByDistributionAndCountry(String distribution, String code2, Integer size);

}
