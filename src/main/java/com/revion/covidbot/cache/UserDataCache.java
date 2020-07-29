package com.revion.covidbot.cache;

import com.revion.covidbot.objects.BotState;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
public class UserDataCache {
    private final Map<Long, BotState> usersBotStates = new HashMap<>();

    public void setUserCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    public BotState getUserCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null)
            botState = BotState.SHOW_MAIN_MENU;

        return botState;
    }

}
