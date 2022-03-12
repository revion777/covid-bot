package com.revion.covidbot.services.locale;

/**
 * @author Maxim Negodyuk created on 23.02.2021
 */
public interface LocaleMessageService {

    /**
     * Получение локализованного сообщения
     *
     * @param message сообщение для локализации
     * @param args    аргументы для сообщения
     * @return локализованное сообщение
     */
    String getMessage(String message, Object... args);

}
