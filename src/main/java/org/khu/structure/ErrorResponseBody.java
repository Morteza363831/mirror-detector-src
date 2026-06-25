package org.khu.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseBody {

    private String errorCode;

    private String messageEn;

    private String messageFa;

    private List<ValidationErrorDto> validationErrors;

    private List<String> invalidFields;

    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now();

}