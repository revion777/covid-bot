package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.show.regions.ShowRegionsSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Component
public class ShowAllNamesHandler implements InputMessageHandler {

    private final ShowRegionsSendMessageService messageService;

    @Override
    public SendMessage handle(Message message) {
        return messageService.init(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_REGIONS;
    }
}



