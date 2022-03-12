package com.revion.covidbot.services.user.builders;

import com.revion.covidbot.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.HashSet;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@Service
public class UserBuilder {

    public UserEntity buildFrom(Message inputMsg) {
        User user = inputMsg.getFrom();
        return UserEntity.builder()
                .id(user.getId())
                .chatId(inputMsg.getChatId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .isBot(user.getIsBot())
                .langCode(user.getLanguageCode())
                .tsCreate(new Date())
                .regions(new HashSet<>())
                .build();
    }

}
