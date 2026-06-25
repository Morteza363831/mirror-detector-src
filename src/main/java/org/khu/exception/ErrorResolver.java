package org.khu.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ErrorResolver {

    // handlers
    @Autowired
    @Qualifier(value = "errorMessageSource")
    private MessageSource messageSource;


    public String resolve(ErrorCode code, Locale locale, Object... args) {

        return messageSource.getMessage(code.getCode(), args, locale);
    }

}
