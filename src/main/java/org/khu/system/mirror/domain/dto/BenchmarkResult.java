package org.khu.system.mirror.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BenchmarkResult {

    @Builder.Default
    private long connectTime = -1;

    @Builder.Default
    private long responseTimeMs = -1;

    @Builder.Default
    private double speedMbps = 0;

    @Builder.Default
    private long contentLength = 0;

    @Builder.Default
    private int status = 0;

    @Builder.Default
    private boolean reachable = false;

}
