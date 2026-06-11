package org.khu.system.mirror.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MirrorResponseDto(

        @JsonProperty("name")
        String name,

        @JsonProperty("display_name")
        String displayName,

        @JsonProperty("self_link")
        String selfLink,

        @JsonProperty("enabled")
        Boolean enabled,

        @JsonProperty("speed")
        String speed,

        @JsonProperty("base_url")
        String baseUrl

) {
}