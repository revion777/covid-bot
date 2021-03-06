package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.UnsubscribeService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class UnsubscribeHandler implements InputMessageHandler {

    private final UnsubscribeService unsubscribeService;

    public UnsubscribeHandler(UnsubscribeService unsubscribeService) {
        this.unsubscribeService = unsubscribeService;
    }

    @Override
    public SendMessage handle(Message inputMsg) {
        return unsubscribeService.handleInputMessage(inputMsg);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.UNSUBSCRIBE;
    }
}