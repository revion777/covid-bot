package com.revion.covidbot.botapi.cache;

import com.revion.covidbot.objects.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Component
public class UserDataCache {
    
    private final Map<Long, BotState> usersBotStates = new HashMap<>();

    public void setUserCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    public BotState getUserCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

}
