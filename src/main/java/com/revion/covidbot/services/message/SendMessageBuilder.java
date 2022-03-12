package com.revion.covidbot.services.message;

import com.revion.covidbot.services.locale.impl.LocaleMessageServiceImpl;
import com.revion.covidbot.utils.Emojis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
public class SendMessageBuilder {

    private final LocaleMessageServiceImpl localeMessageService;

    public SendMessage getInfoReplyMessage(String chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, Emojis.INFO));
    }

    public SendMessage getSuccessEmojiMessage(String chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, Emojis.SUCCESS_MARK));
    }

    public SendMessage getErrorEmojiMessage(String chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, Emojis.ERROR));
    }

    public SendMessage getQuestionEmojiMessage(String chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, Emojis.ASK));
    }

    public SendMessage getMessageWithArgs(String chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, args));
    }

    public SendMessage getPlainTextMessage(String chatId, String text) {
        return createMessage(chatId, text, null);
    }

    public SendMessage getMainMenuMessage(String chatId, String replyMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return createMessage(chatId, localeMessageService.getMessage(replyMessage, Emojis.BULB), replyKeyboardMarkup);
    }

    private SendMessage createMessage(String chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        var sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        return sendMessage;
    }

}
