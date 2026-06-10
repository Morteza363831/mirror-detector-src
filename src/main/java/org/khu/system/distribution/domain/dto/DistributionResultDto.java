package org.khu.system.distribution.domain.dto;

public record DistributionResultDto(

        String logoLink,

        String name,

        String displayName,

        Boolean active,

        Boolean isPrivate,

        String cdImageMirrorsCollectionLink

) {
}