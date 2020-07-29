package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.InfoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class InfoHandler implements InputMessageHandler {

    private final InfoService infoService;

    public InfoHandler(InfoService infoService) {
        this.infoService = infoService;
    }

    @Override
    public SendMessage handle(Message message) {
        return infoService.handleInputMessage(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.HELP;
    }
}
