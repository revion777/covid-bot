package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.menu.MainMenuSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Component
public class MainMenuHandler implements InputMessageHandler {

    private final MainMenuSendMessageService mainMenuSendMessageService;

    @Override
    public SendMessage handle(Message message) {
        return mainMenuSendMessageService.init(String.valueOf(message.getChatId()));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
