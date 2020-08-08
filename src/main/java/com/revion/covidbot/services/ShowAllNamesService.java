package com.revion.covidbot.services;


import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.utils.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
public class ShowAllNamesService {

    private final RegionService regionService;
    private final UserDataCache userDataCache;

    public ShowAllNamesService(RegionService regionService,
                               UserDataCache userDataCache) {
        this.regionService = regionService;
        this.userDataCache = userDataCache;
    }

    public SendMessage handleInputMsg(Message message) {
        StringBuilder replyText = new StringBuilder("*Наименования:*\n");

        regionService.findAllRegions()
                .stream()
                .sorted(Comparator.comparing(RegionEntity::getTitle))
                .forEach(region -> replyText
                        .append("_")
                        .append(region.getTitle())
                        .append("_\n"));

        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

        return BotUtils.getMessage(message.getChatId(), replyText.toString());
    }
}



