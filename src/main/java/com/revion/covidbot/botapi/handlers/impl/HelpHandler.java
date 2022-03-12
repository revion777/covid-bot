package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.help.HelpSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
public class HelpHandler implements InputMessageHandler {

    private final HelpSendMessageService helpSendMessageService;

    @Override
    public SendMessage handle(Message message) {
        return helpSendMessageService.init(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.HELP;
    }

}
