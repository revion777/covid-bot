package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.SendMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Component
public class DeleteRegionAskHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final SendMessageBuilder messagesService;

    @Override
    public SendMessage handle(Message message) {
        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.DELETE_REGION);
        return messagesService.getQuestionEmojiMessage(String.valueOf(message.getChatId()), "reply.region.choose");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_TO_CHOOSE_REGION_TO_DELETE;
    }

}



