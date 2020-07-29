package com.revion.covidbot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
public class BotUtils {

    public static SendMessage getMessage(long chatId, String text) {
        return createMessage(chatId, text, null);
    }

    public static SendMessage getMessage(long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return createMessage(chatId, text, replyKeyboardMarkup);
    }

    private static SendMessage createMessage(long chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null)
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

}
