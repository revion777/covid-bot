package com.revion.covidbot.services.locale.impl;

import com.revion.covidbot.services.locale.LocaleMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Service
public class LocaleMessageServiceImpl implements LocaleMessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageServiceImpl(@Value("${localeTag}") String localeTag,
                                    MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }

}
