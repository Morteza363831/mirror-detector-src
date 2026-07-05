package org.khu.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageConfigTest {

    private final ErrorMessageConfig config = new ErrorMessageConfig();

    @Test
    void shouldCreateErrorMessageSource() {

        MessageSource messageSource = config.errorMessageSource();

        assertNotNull(messageSource);
        assertInstanceOf(ResourceBundleMessageSource.class, messageSource);
    }

    @Test
    void shouldResolveMessages() {

        MessageSource messageSource = config.errorMessageSource();

        assertDoesNotThrow(() ->
                messageSource.getMessage(
                        "error.default.en",
                        null,
                        Locale.ENGLISH
                )
        );
    }
}