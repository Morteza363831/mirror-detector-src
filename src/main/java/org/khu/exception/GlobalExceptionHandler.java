package org.khu.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khu.structure.ErrorResponseBody;
import org.khu.structure.ValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // default properties
    private static final Locale EN = Locale.ENGLISH;
    private static final Locale FA = Locale.forLanguageTag("fa");

    // resolver
    private final ErrorResolver errorResolver;


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseBody> handleCustomException(CustomException ex) {

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponseBody.builder()
                                .errorCode(ex.getErrorCode().name())
                                .messageEn(
                                        errorResolver.resolve(
                                                ex.getErrorCode(),
                                                EN,
                                                ex.getArgs()))
                                .messageFa(
                                        errorResolver.resolve(
                                                ex.getErrorCode(),
                                                FA,
                                                ex.getArgs()))
                                .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseBody> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        ErrorResponseBody.builder()
                                .errorCode(
                                        ErrorCode.METHOD_NOT_ALLOWED.name())
                                .messageEn(
                                        errorResolver.resolve(
                                                ErrorCode.METHOD_NOT_ALLOWED,
                                                EN))
                                .messageFa(
                                        errorResolver.resolve(
                                                ErrorCode.METHOD_NOT_ALLOWED,
                                                FA))
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> handleValidation(MethodArgumentNotValidException ex) {

        List<ValidationErrorDto> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError ->
                                ValidationErrorDto.builder()
                                        .field(fieldError.getField())
                                        .message(fieldError.getDefaultMessage())
                                        .build()
                        )
                        .toList();

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponseBody.builder()
                                .errorCode(
                                        ErrorCode.VALIDATION_ERROR.name())
                                .messageEn(
                                        errorResolver.resolve(
                                                ErrorCode.VALIDATION_ERROR,
                                                EN))
                                .messageFa(
                                        errorResolver.resolve(
                                                ErrorCode.VALIDATION_ERROR,
                                                FA))
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleConstraintViolation(ConstraintViolationException ex) {

        List<String> fields =
                ex.getConstraintViolations()
                        .stream()
                        .map(v -> v.getPropertyPath().toString())
                        .toList();

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponseBody.builder()
                                .errorCode(
                                        ErrorCode.VALIDATION_ERROR.name())
                                .messageEn(
                                        errorResolver.resolve(
                                                ErrorCode.VALIDATION_ERROR,
                                                EN))
                                .messageFa(
                                        errorResolver.resolve(
                                                ErrorCode.VALIDATION_ERROR,
                                                FA))
                                .invalidFields(fields)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> handleUnhandled(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponseBody.builder()
                                .errorCode(
                                        ErrorCode.INTERNAL_SERVER_ERROR.name())
                                .messageEn(
                                        errorResolver.resolve(
                                                ErrorCode.INTERNAL_SERVER_ERROR,
                                                EN))
                                .messageFa(
                                        errorResolver.resolve(
                                                ErrorCode.INTERNAL_SERVER_ERROR,
                                                FA))
                                .build()
                );
    }
}