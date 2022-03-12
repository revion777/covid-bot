package com.revion.covidbot.services.message.region;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotCommand;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.SendMessageBuilder;
import com.revion.covidbot.services.region.RegionService;
import com.revion.covidbot.services.user.UserService;
import com.revion.covidbot.utils.Emojis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public abstract class RegionSendMessageService {

    final RegionService regionService;
    final UserDataCache userDataCache;
    final UserService userService;
    final SendMessageBuilder messageBuilder;

    public SendMessage init(Message inputMsg) {
        return regionService.findRegionByTitle(inputMsg.getText()).stream()
                .findFirst()
                .map(region -> getSuccessfulSendMessage(inputMsg, region))
                .orElseGet(() -> getFailedSendMessage(inputMsg));
    }

    private SendMessage getSuccessfulSendMessage(Message inputMsg, RegionEntity region) {
        return userService.findById(inputMsg.getFrom().getId())
                .map(savedUser -> initGeneratingReplyWithSavedUser(savedUser, region))
                .orElseGet(() -> initGeneratingReplyWithNewUser(inputMsg, region));
    }

    private SendMessage initGeneratingReplyWithNewUser(Message inputMsg, RegionEntity region) {
        log.info(LogMessage.USER_NOT_FOUND, inputMsg.getFrom().getFirstName());

        UserEntity newUser = userService.createNewUser(inputMsg);
        return initGeneratingReplyWithSavedUser(newUser, region);
    }

    private SendMessage initGeneratingReplyWithSavedUser(UserEntity savedUser, RegionEntity region) {
        SendMessage sendMessage = generateSpecificReplyToUser(savedUser, region);
        userService.updateUser(savedUser);
        userDataCache.setUserCurrentBotState(savedUser.getId(), BotState.SHOW_MAIN_MENU);

        log.info(LogMessage.REGION_SUCCESSFULLY_UPDATED, savedUser.getId(), region.getTitle());
        return sendMessage;
    }

    abstract SendMessage generateSpecificReplyToUser(UserEntity user, RegionEntity region);

    private SendMessage getFailedSendMessage(Message inputMsg) {
        User inputUser = inputMsg.getFrom();
        String userInputText = inputMsg.getText();

        log.info(LogMessage.REGION_INVALID_INPUT, inputUser.getId(), userInputText);
        return messageBuilder.getMessageWithArgs(String.valueOf(inputMsg.getChatId()), "reply.region.invalidInput",
                Emojis.NOTIFICATION_MARK_FAILED, BotCommand.ADD_REGION.getCommand());
    }

}
