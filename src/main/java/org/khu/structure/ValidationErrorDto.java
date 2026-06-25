package org.khu.structure;

import lombok.Builder;

@Builder
public record ValidationErrorDto(

        String field,

        String message

    ) {}