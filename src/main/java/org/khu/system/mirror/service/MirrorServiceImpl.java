package org.khu.system.mirror.service;

import lombok.RequiredArgsConstructor;
import org.khu.logging.DebugLogging;
import org.khu.logging.ErrorLogging;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.service.CountryService;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.service.DistributionService;
import org.khu.system.mirror.domain.dto.MirrorBenchmarkDto;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.domain.mapper.MirrorMapper;
import org.khu.system.mirror.handler.MirrorRequestHandler;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ErrorLogging
@DebugLogging
@Service
@RequiredArgsConstructor
public class MirrorServiceImpl implements MirrorService {

    // tools
    private final MirrorMapper mirrorMapper;

    // handler
    private final MirrorRequestHandler mirrorRequestHandler;

    // service
    private final CountryService countryService;
    private final DistributionService distributionService;
    private final MirrorBenchmarkService mirrorBenchmarkService;


    /// Client scope functionalities

    @Override
    public List<MirrorResultDto> getAllByDistributionAndCountry(String distribution, String code2, Integer size) {

        Country foundedCountry = countryService.getModelByCode2(code2);

        Distribution foundedDistro = distributionService.getModelByName(distribution);

        if (validateIsNull(foundedCountry, foundedDistro)) {
            return List.of();
        }

        String countryEncodedLink = encodeParamLink(foundedCountry.getSelfLink());

        List<MirrorResponseDto> mirrorResponseDtoList =
                mirrorRequestHandler.fetchMirrorsByDistributionAndCountry(foundedDistro.getName(), countryEncodedLink, size);

        return mirrorResponseDtoList
                .stream()
                .map(mirrorMapper::toResultDto)
                .toList();
    }

    @Override
    public List<MirrorBenchmarkDto> testAllByDistributionAndCountry(String distribution, String code2, Integer size) {

        List<MirrorResultDto> mirrorResultDtoList = getAllByDistributionAndCountry(distribution, code2, size);

        List<MirrorBenchmarkDto> mirrorBenchmarkDtoList = mirrorBenchmarkService.benchmarkAll(mirrorResultDtoList);

        return mirrorBenchmarkDtoList;

    }

    private boolean validateIsNull(Country foundedCountry, Distribution foundedDistro) {

        return foundedCountry == null || foundedDistro == null;

    }

    private String encodeParamLink(String link) {

        return URLEncoder.encode(link, StandardCharsets.UTF_8);
    }


    @Override
    public List<MirrorResultDto> getAllByDistribution(String distribution, Integer size) {

        Distribution foundedDistribution = distributionService.getModelByName(distribution);

        if (foundedDistribution == null) {
            return List.of();
        }

        List<MirrorResponseDto> mirrorResponseDtoList =
                mirrorRequestHandler.fetchMirrorsByDistribution(foundedDistribution.getArchiveMirrorsCollectionLink(), size);

        return mirrorResponseDtoList
                .stream()
                .map(mirrorMapper::toResultDto)
                .toList();

    }

    @Override
    public List<MirrorBenchmarkDto> testAllByDistribution(String distribution, Integer size) {

        List<MirrorResultDto> mirrorResultDtoList = getAllByDistribution(distribution, size);

        List<MirrorBenchmarkDto> mirrorBenchmarkDtoList = mirrorBenchmarkService.benchmarkAll(mirrorResultDtoList);

        return mirrorBenchmarkDtoList;
    }
}
