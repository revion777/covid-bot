package com.revion.covidbot.botapi.handlers;

import com.revion.covidbot.objects.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
public interface InputMessageHandler {

    SendMessage handle(Message message);

    BotState getHandlerName();
}
