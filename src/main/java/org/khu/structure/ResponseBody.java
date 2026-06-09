package org.khu.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBody {

    private Integer page;

    private Integer size;

    private Long totalElements;

    private Long totalPages;

    private String message;

    @Builder.Default
    private Object data = Map.of();

    @Builder.Default
    private Object errors = Map.of();

    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now();

}
