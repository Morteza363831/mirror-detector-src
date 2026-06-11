package org.khu.system.mirror.domain.dto;

public record MirrorResultDto(

        String name,

        String displayName,

        Boolean enabled,

        String baseUrl

) {
}