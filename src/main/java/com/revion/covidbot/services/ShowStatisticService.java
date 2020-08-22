package com.revion.covidbot.services;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.botapi.handlers.impl.AddRegionHandler;
import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.utils.BotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Slf4j
@Component
public class ShowStatisticService {

    private final UserDataCache userDataCache;
    private final UserService userService;
    private final RegionService regionService;
    private final AddRegionHandler addRegionHandler;
    private final OverallService overallService;

    private long userId;
    private long chatId;
    private UserEntity savedUser;
    private Message inputMsg;
    private Set<RegionEntity> regions;

    public ShowStatisticService(UserDataCache userDataCache,
                                UserService userService,
                                RegionService regionService,
                                AddRegionHandler addRegionHandler,
                                OverallService overallService) {
        this.userDataCache = userDataCache;
        this.userService = userService;
        this.regionService = regionService;
        this.addRegionHandler = addRegionHandler;
        this.overallService = overallService;
    }

    public SendMessage handleInputMessage(Message inputMsg) {
        this.inputMsg = inputMsg;
        userId = inputMsg.getFrom().getId();
        chatId = inputMsg.getChatId();
        savedUser = userService.findById(userId).orElse(null);

        return getMessageStatistic();
    }

    @Transactional
    public void sendMsgStatisticToAllUsers(CovidBot covidBot) {
        List<UserEntity> userList = userService.findAllUsers();
        userList.forEach(user -> sendMsgStatisticToUser(user, covidBot));
    }

    private void sendMsgStatisticToUser(UserEntity savedUser, CovidBot covidBot) {
        this.inputMsg = null;
        this.savedUser = savedUser;
        userId = savedUser.getId();
        chatId = savedUser.getChatId();

        SendMessage sendMessage = getMessageStatistic();
        if (sendMessage != null) {
            try {
                covidBot.sendMsg(sendMessage);
                log.info(LogMessage.BOT_SEND_MSG_SUCCESS, sendMessage.getText());
            } catch (TelegramApiException ex) {
                handleTelegramException(ex);
            }
        }
    }

    private void handleTelegramException(TelegramApiException ex) {
        try {
            TelegramApiRequestException apiReqEx = (TelegramApiRequestException) ex;
            log.error(LogMessage.BOT_SEND_MSG_ERROR, apiReqEx.getApiResponse(), savedUser.getFirstName());

            int errorCode = apiReqEx.getErrorCode();
            if (errorCode == HttpStatus.FORBIDDEN.value())
                userService.deleteUser(savedUser);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private SendMessage getMessageStatistic() {
        SendMessage sendMessage = null;

        if (savedUser != null) {
            regions = savedUser.getRegions();

            if (regions.isEmpty() && inputMsg != null) {
                log.info(LogMessage.REGIONS_NOT_FOUND, savedUser.getFirstName());
                sendMessage = generateMessageFromAddRegionHandler();

            } else if (!regions.isEmpty()) {
                log.info(LogMessage.REGIONS_FOUND, savedUser.getFirstName());
                sendMessage = createMessageStatistic();

            } else {
                log.info(LogMessage.REGIONS_NOT_FOUND, savedUser.getFirstName());
            }

        } else if (inputMsg != null) {
            log.info(LogMessage.USER_NOT_FOUND, inputMsg.getFrom().getFirstName());
            sendMessage = generateMessageFromAddRegionHandler();
        }

        return sendMessage;
    }

    private SendMessage generateMessageFromAddRegionHandler() {
        userDataCache.setUserCurrentBotState(userId, BotState.ADD_REGION);
        return addRegionHandler.handle(inputMsg);
    }

    private SendMessage createMessageStatistic() {
        userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        OverallEntity overall = overallService.findOverall();
        StringBuilder replyText = overallService.getOverallInfo(overall);
        regions.stream()
                .sorted(Comparator.comparing(RegionEntity::getTitle))
                .forEach(region -> regionService.fillReplyTextByRegionInfo(replyText, region));

        return BotUtils.getMessage(chatId, replyText.toString());
    }

}
