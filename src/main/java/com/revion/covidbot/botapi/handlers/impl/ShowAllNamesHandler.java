package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.ShowAllNamesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class ShowAllNamesHandler implements InputMessageHandler {

    private final ShowAllNamesService showAllNamesService;

    public ShowAllNamesHandler(ShowAllNamesService showAllNamesService) {
        this.showAllNamesService = showAllNamesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return showAllNamesService.handleInputMsg(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_REGIONS;
    }
}



