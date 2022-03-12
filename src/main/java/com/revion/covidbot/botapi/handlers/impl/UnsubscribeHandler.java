package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.unsubscribe.UnsubscribeSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Component
public class UnsubscribeHandler implements InputMessageHandler {

    private final UnsubscribeSendMessageService messageService;

    @Override
    public SendMessage handle(Message inputMsg) {
        return messageService.init(inputMsg);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.UNSUBSCRIBE;
    }
}