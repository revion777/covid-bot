package com.revion.covidbot.botapi;

import com.revion.covidbot.objects.logging.LogMessage;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Slf4j
public class CovidBot extends SpringWebhookBot {

    @Autowired
    private TelegramFacade telegramFacade;

    private String botPath;
    private String botUsername;
    private String botToken;

    public CovidBot(SetWebhook setWebhook) {
        super(setWebhook);
    }

    @Override
    public BotApiMethod<Message> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMsg(SendMessage sendMessage) throws TelegramApiException {
        execute(sendMessage);
        log.info(LogMessage.BOT_SEND_MSG_SUCCESS, sendMessage.getText());
    }

}
