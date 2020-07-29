package com.revion.covidbot.services;


import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.RegionEnum;
import com.revion.covidbot.utils.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
public class ShowAllNamesService {

    private final UserDataCache userDataCache;

    public ShowAllNamesService(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    public SendMessage handleInputMsg(Message message) {
        StringBuilder replyText = new StringBuilder("Код - Наименование:\n");
        Arrays.asList(RegionEnum.values()).forEach(region -> replyText.append(region.getCode())
                .append(" - ")
                .append(region.getTitle())
                .append("\n")
        );

        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

        return BotUtils.getMessage(message.getChatId(), replyText.toString());
    }
}



