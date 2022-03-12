package com.revion.covidbot.services.message.show.statistic;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.botapi.handlers.impl.AddRegionHandler;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.SendMessageBuilder;
import com.revion.covidbot.services.overall.OverallService;
import com.revion.covidbot.services.region.RegionService;
import com.revion.covidbot.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;
import java.util.Set;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ShowStatisticSendMessageService {

    private final UserDataCache userDataCache;
    private final UserService userService;
    private final RegionService regionService;
    private final AddRegionHandler addRegionHandler;
    private final OverallService overallService;
    private final SendMessageBuilder sendMessageBuilder;

    public SendMessage init(Message inputMsg) {
        UserEntity savedUser = userService.findById(inputMsg.getFrom().getId()).orElse(null);
        return getMessageStatistic(inputMsg, savedUser);
    }

    public SendMessage getMessageStatistic(Message inputMsg, UserEntity savedUser) {
        if (savedUser != null) {
            Set<RegionEntity> regions = savedUser.getRegions();

            if (regions.isEmpty()) {
                log.info(LogMessage.REGIONS_NOT_FOUND, savedUser.getId());
                return createMessageByAddRegionHandler(inputMsg);
            }

            log.info(LogMessage.REGIONS_FOUND, savedUser.getId());
            userDataCache.setUserCurrentBotState(inputMsg.getFrom().getId(), BotState.SHOW_MAIN_MENU);
            return createMessage(inputMsg.getChatId(), regions);
        }
        log.info(LogMessage.USER_NOT_FOUND, inputMsg.getFrom().getId());
        return createMessageByAddRegionHandler(inputMsg);
    }

    public SendMessage getMessageStatistic(UserEntity savedUser) {
        SendMessage sendMessage = null;
        if (savedUser != null) {
            Set<RegionEntity> regions = savedUser.getRegions();
            if (!regions.isEmpty()) {
                log.info(LogMessage.REGIONS_FOUND, savedUser.getId());
                sendMessage = createMessage(savedUser.getChatId(), regions);
            }
            log.info(LogMessage.REGIONS_NOT_FOUND, savedUser.getId());
        }
        return sendMessage;
    }

    private SendMessage createMessageByAddRegionHandler(Message inputMsg) {
        userDataCache.setUserCurrentBotState(inputMsg.getFrom().getId(), BotState.ADD_REGION);
        return addRegionHandler.handle(inputMsg);
    }

    private SendMessage createMessage(Long chatId, Set<RegionEntity> regions) {
        StringBuilder replyText = overallService.getOverallInfo();
        regions.stream()
                .sorted(Comparator.comparing(RegionEntity::getTitle))
                .forEach(region -> regionService.fillReplyTextWithRegionInfo(replyText, region));

        return sendMessageBuilder.getPlainTextMessage(String.valueOf(chatId), replyText.toString());
    }

}
