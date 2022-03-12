package com.revion.covidbot.botapi;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.objects.BotCommand;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.SendMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.revion.covidbot.objects.logging.LogMessage.BOT_HANDLE_INPUT_MSG_ERROR;
import static com.revion.covidbot.objects.logging.LogMessage.BOT_NEW_INPUT_MESSAGE;
import static com.revion.covidbot.objects.logging.LogMessage.BOT_SEND_MSG_SUCCESS;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramFacade {

    private final UserDataCache userDataCache;
    private final BotStateContext botStateContext;
    private final SendMessageBuilder messagesService;

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;
        Message inputMsg = update.getMessage();

        if (inputMsg != null && inputMsg.hasText()) {
            log.info(BOT_NEW_INPUT_MESSAGE, inputMsg);
            replyMessage = handleInputMessage(inputMsg);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message inputMsg) {
        Long userId = inputMsg.getFrom().getId();

        BotState botState = Optional.ofNullable(BotCommand.getBotStateByCommand(inputMsg.getText()))
                .orElseGet(() -> userDataCache.getUserCurrentBotState(userId));
        userDataCache.setUserCurrentBotState(userId, botState);

        try {
            log.info(BOT_SEND_MSG_SUCCESS, inputMsg.getText());
            return botStateContext.processInputMessage(botState, inputMsg);
        } catch (RuntimeException ex) {
            log.error(BOT_HANDLE_INPUT_MSG_ERROR, ex);
            return messagesService.getErrorEmojiMessage(String.valueOf(inputMsg.getChatId()), "reply.bot.error");
        }
    }

}
