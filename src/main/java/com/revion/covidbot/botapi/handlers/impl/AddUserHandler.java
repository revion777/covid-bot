package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class AddUserHandler implements InputMessageHandler {

    private final UserService userService;

    public AddUserHandler(UserService userService) {
        this.userService = userService;
    }

    public SendMessage handle(Message inputMsg) {
        return userService.handleInputMessage(inputMsg);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADD_USER;
    }
}
