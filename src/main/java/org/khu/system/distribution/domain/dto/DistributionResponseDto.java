package org.khu.system.distribution.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DistributionResponseDto(

        @JsonProperty("self_link")
        String selfLink,

        @JsonProperty("name")
        String name,

        @JsonProperty("display_name")
        String displayName,

        @JsonProperty("title")
        String title,

        @JsonProperty("active")
        Boolean isActive,

        @JsonProperty("private")
        Boolean isPrivate,

        @JsonProperty("icon_link")
        String iconLink,

        @JsonProperty("logo_link")
        String logoLink,

        @JsonProperty("archive_mirrors_collection_link")
        String archiveMirrorsCollectionLink,

        @JsonProperty("cdimage_mirrors_collection_link")
        String cdImageMirrorsCollectionLink

) {
}