package org.khu.system.mirror.domain.dto;

import lombok.Builder;

@Builder
public record MirrorBenchmarkDto(

        String name,

        String displayName,

        String baseUrl,

        boolean reachable,

        int httpStatus,

        long connectTimeMs,

        long responseTimeMs,

        double downloadSpeedMbps,

        long contentLength,

        double score

) {
}