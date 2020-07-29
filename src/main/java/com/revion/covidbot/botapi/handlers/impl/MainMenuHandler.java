package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.MainMenuService;
import com.revion.covidbot.services.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class MainMenuHandler implements InputMessageHandler {

    private final ReplyMessagesService messagesService;
    private final MainMenuService mainMenuService;

    public MainMenuHandler(ReplyMessagesService messagesService,
                           MainMenuService mainMenuService) {
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        String replyText = messagesService.getInfoText("reply.mainMenu.welcomeMessage");

        return mainMenuService.getMainMenuMessage(chatId, replyText);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
