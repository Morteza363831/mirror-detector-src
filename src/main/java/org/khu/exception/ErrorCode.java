package org.khu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // model
    MODEL_NOT_FOUND("error.model.not.found"),
    MODEL_ALREADY_EXISTS("error.model.already.exists"),

    // connection
    COULD_NOT_ESTABLISH_CONNECTION("error.could.not.establish.connection"),
    CONNECTION_TIMEOUT("error.connection.timeout"),

    // processing
    COULD_NOT_PROCESS_RESPONSE("error.could.not.process.response"),
    INVALID_RESPONSE("error.invalid.response"),

    // cache
    CACHE_NOT_FOUND("error.cache.not.found"),
    CACHE_COULD_NOT_LOAD("error.cache.could.not.load"),

    // request
    INVALID_REQUEST("error.invalid.request"),
    INVALID_PARAMETER("error.invalid.parameter"),

    // validation
    VALIDATION_ERROR("error.validation"),

    // internal,
    METHOD_NOT_ALLOWED("error.method.not.allowed"),
    INTERNAL_SERVER_ERROR("error.internal.server.error");

    private final String code;
}