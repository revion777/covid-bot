package com.revion.covidbot.services.extractor;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.show.statistic.ShowStatisticSendMessageService;
import com.revion.covidbot.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageSender {

    private final ShowStatisticSendMessageService showStatisticService;
    private final CovidBot covidBot;
    private final UserService userService;

    @Transactional
    public void sendMsgStatisticToUsers() {
        userService.findAllUsers().forEach(this::send);
    }

    private void send(UserEntity savedUser) {
        SendMessage sendMessage = showStatisticService.getMessageStatistic(savedUser);
        if (sendMessage != null) {
            try {
                covidBot.sendMsg(sendMessage);
            } catch (TelegramApiException ex) {
                TelegramApiRequestException apiReqEx = (TelegramApiRequestException) ex;
                log.error(LogMessage.BOT_SEND_MSG_ERROR, apiReqEx.getApiResponse(), savedUser.getFirstName());

                if (apiReqEx.getErrorCode() == HttpStatus.FORBIDDEN.value()) {
                    userService.deleteUser(savedUser);
                }
            }
        }
    }

}
