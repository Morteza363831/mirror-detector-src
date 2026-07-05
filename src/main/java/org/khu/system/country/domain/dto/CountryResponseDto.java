package org.khu.system.country.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CountryResponseDto
        (

                @JsonProperty("self_link")
                String selfLink,

                @JsonProperty("resource_type_link")
                String resourceTypeLink,

                @JsonProperty("iso3166code2")
                String code2,

                @JsonProperty("iso3166code3")
                String code3,

                @JsonProperty("name")
                String name,

                @JsonProperty("title")
                String title,

                @JsonProperty("description")
                String description

        ) {}
