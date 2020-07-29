package com.revion.covidbot.services;

import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final ReplyMessagesService messagesService;
    private final MainMenuService mainMenuService;

    private long chatId;

    public UserService(UserRepo userRepo,
                       ReplyMessagesService messagesService,
                       MainMenuService mainMenuService) {
        this.userRepo = userRepo;
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
    }

    public SendMessage handleInputMessage(Message inputMsg) {
        initUserSaving(inputMsg);

        String replyText = messagesService.getInfoText("reply.mainMenu.welcomeMessage");
        return mainMenuService.getMainMenuMessage(chatId, replyText);
    }

    public UserEntity initUserSaving(Message inputMsg) {
        User user = inputMsg.getFrom();
        chatId = inputMsg.getChatId();

        Optional<UserEntity> savedUser = findById(user.getId());
        if (savedUser.isEmpty()) {
            UserEntity newUser = UserEntity.builder()
                    .id(Long.valueOf(user.getId()))
                    .chatId(inputMsg.getChatId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userName(user.getUserName())
                    .isBot(user.getBot())
                    .languageCode(user.getLanguageCode())
                    .tsCreate(new Date())
                    .regions(new HashSet<>())
                    .build();

            return saveUser(newUser);
        } else {
            log.info(LogMessage.USER_ALREADY_SAVED, savedUser);
            return savedUser.get();
        }
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(long id) {
        return userRepo.findById(id);
    }

    @Transactional
    public UserEntity saveUser(UserEntity user) {
        log.info(LogMessage.USER_SUCCESSFULLY_SAVED, user);
        return userRepo.save(user);
    }

    @Transactional
    public void deleteUser(UserEntity user) {
        userRepo.delete(user);
        log.info(LogMessage.USER_DELETED, user);
    }
}
