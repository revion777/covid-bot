package com.revion.covidbot.botapi;

import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.objects.BotCommand;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
@Slf4j
public class TelegramFacade {

    private final UserDataCache userDataCache;
    private final BotStateContext botStateContext;
    private final ReplyMessagesService messagesService;

    private User inputUser;

    public TelegramFacade(UserDataCache userDataCache,
                          BotStateContext botStateContext,
                          ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
        this.messagesService = messagesService;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            inputUser = message.getFrom();

            log.info(LogMessage.BOT_NEW_INPUT_MESSAGE, inputUser.getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
            log.info(LogMessage.BOT_SEND_MSG_SUCCESS, replyMessage.getText());
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        long userId = inputUser.getId();

        BotState botState = switch (inputMsg) {
            case BotCommand.START -> BotState.ADD_USER;
            case BotCommand.SHOW_REGIONS -> BotState.SHOW_REGIONS;
            case BotCommand.SHOW_STATISTIC -> BotState.SHOW_STATISTIC;
            case BotCommand.ADD_REGION -> BotState.ADD_REGION;
            case BotCommand.DELETE_REGION -> BotState.DELETE_REGION;
            case BotCommand.HELP -> BotState.HELP;
            case BotCommand.UNSUBSCRIBE -> BotState.UNSUBSCRIBE;
            default -> userDataCache.getUserCurrentBotState(userId);
        };

        userDataCache.setUserCurrentBotState(userId, botState);

        try {
            return botStateContext.processInputMessage(botState, message);
        } catch (Exception ex) {
            log.error(LogMessage.BOT_HANDLE_INPUT_MSG_ERROR, ex);
            return messagesService.getErrorReplyMessage(message.getChatId(), "reply.bot.error");
        }
    }
}
