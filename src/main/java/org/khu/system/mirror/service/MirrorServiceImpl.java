package org.khu.system.mirror.service;

import lombok.RequiredArgsConstructor;
import org.khu.system.country.domain.model.Country;
import org.khu.system.country.service.CountryService;
import org.khu.system.distribution.domain.model.Distribution;
import org.khu.system.distribution.service.DistributionService;
import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.domain.mapper.MirrorMapper;
import org.khu.system.mirror.handler.MirrorRequestHandler;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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


    private boolean validateIsNull(Country foundedCountry, Distribution foundedDistro) {

        return foundedCountry == null || foundedDistro == null;

    }

    private String encodeParamLink(String link) {

        return URLEncoder.encode(link, StandardCharsets.UTF_8);
    }

}
