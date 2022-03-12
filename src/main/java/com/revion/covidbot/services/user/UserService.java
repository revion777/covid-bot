package com.revion.covidbot.services.user;

import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.UserRepository;
import com.revion.covidbot.services.message.menu.MainMenuSendMessageService;
import com.revion.covidbot.services.user.builders.UserBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBuilder userBuilder;
    private final MainMenuSendMessageService mainMenuSendMessageService;

    public SendMessage handleInputMessage(Message inputMsg) {
        initSaving(inputMsg);
        return mainMenuSendMessageService.init(String.valueOf(inputMsg.getChatId()));
    }

    private void initSaving(Message inputMsg) {
        Optional<UserEntity> savedUser = findById(inputMsg.getFrom().getId());
        savedUser.ifPresentOrElse(user -> log.info(LogMessage.USER_ALREADY_SAVED, user), () -> createNewUser(inputMsg));
    }

    public UserEntity createNewUser(Message inputMsg) {
        UserEntity newUser = userBuilder.buildFrom(inputMsg);
        return userRepository.save(newUser);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(long id) {
        return userRepository.findById(id);
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
        log.info(LogMessage.USER_SUCCESSFULLY_SAVED, user);
    }

    @Transactional
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
        log.info(LogMessage.USER_DELETED, user);
    }

}
