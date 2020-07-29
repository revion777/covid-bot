package com.revion.covidbot.services;

import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Slf4j
@Component
public class UnsubscribeService {

    private final UserDataCache userDataCache;
    private final UserService userService;
    private final ReplyMessagesService messagesService;

    public UnsubscribeService(UserDataCache userDataCache,
                              UserService userService,
                              ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.userService = userService;
        this.messagesService = messagesService;
    }

    public SendMessage handleInputMessage(Message inputMsg) {
        User user = inputMsg.getFrom();
        long chatId = inputMsg.getChatId();
        SendMessage replyMsg;

        Optional<UserEntity> savedUser = userService.findById(user.getId());
        if (savedUser.isPresent()) {
            savedUser.get().getRegions().clear();
            userService.deleteUser(savedUser.get());

            replyMsg = messagesService.getSuccessReplyMessage(chatId, "reply.user.deleted");
        } else {
            replyMsg = messagesService.getInfoReplyMessage(chatId, "reply.user.alreadyDeleted");
            log.info(LogMessage.USER_ALREADY_DELETED, user.getFirstName());
        }

        userDataCache.setUserCurrentBotState(user.getId(), BotState.SHOW_MAIN_MENU);

        return replyMsg;
    }

}
