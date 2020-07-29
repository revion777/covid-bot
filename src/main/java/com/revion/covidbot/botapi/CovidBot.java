package com.revion.covidbot.botapi;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Getter
@Setter
@Slf4j
public class CovidBot extends TelegramWebhookBot {

    @Autowired
    private TelegramFacade telegramFacade;

    private String botPath;
    private String botUsername;
    private String botToken;

    public CovidBot() {
        super();
    }

    public CovidBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    public void sendMsg(SendMessage sendMessage) throws TelegramApiException {
        execute(sendMessage);
    }
}
