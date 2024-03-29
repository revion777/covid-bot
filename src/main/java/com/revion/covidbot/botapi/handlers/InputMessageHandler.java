package com.revion.covidbot.botapi.handlers;

import com.revion.covidbot.objects.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public interface InputMessageHandler {

    /**
     * Обработчик полученных сообщений
     *
     * @param message входящее сообщение
     * @return сообщение для отправки боту
     */
    SendMessage handle(Message message);

    /**
     * Наименование обработчика сообщений
     *
     * @return состояние, в котором находится бот
     */
    BotState getHandlerName();
}
