package com.revion.covidbot.services.message.unsubscribe;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.SendMessageBuilder;
import com.revion.covidbot.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UnsubscribeSendMessageService {

    private final UserDataCache userDataCache;
    private final UserService userService;
    private final SendMessageBuilder messageBuilder;

    public SendMessage init(Message inputMsg) {
        SendMessage replyMsg = userService.findById(inputMsg.getFrom().getId())
                .map(savedUser -> getSuccessfulSendMessage(savedUser, inputMsg))
                .orElseGet(() -> getFailedSendMessage(inputMsg));

        userDataCache.setUserCurrentBotState(inputMsg.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        return replyMsg;
    }


    private SendMessage getSuccessfulSendMessage(UserEntity savedUser, Message inputMsg) {
        savedUser.getRegions().clear();
        userService.deleteUser(savedUser);

        return messageBuilder.getSuccessEmojiMessage(String.valueOf(inputMsg.getChatId()), "reply.user.deleted");
    }

    private SendMessage getFailedSendMessage(Message inputMsg) {
        log.info(LogMessage.USER_ALREADY_DELETED, inputMsg.getFrom().getFirstName());
        return messageBuilder.getInfoReplyMessage(String.valueOf(inputMsg.getChatId()), "reply.user.alreadyDeleted");
    }

}
