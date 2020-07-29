package com.revion.covidbot.botapi.handlers.impl;

import com.revion.covidbot.botapi.handlers.InputMessageHandler;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Component
public class AddRegionHandler implements InputMessageHandler {

    private final RegionService regionService;

    public AddRegionHandler(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public SendMessage handle(Message message) {
        regionService.setHandlerTypeAdd(true);
        regionService.setStartState(BotState.ADD_REGION);
        regionService.setAskState(BotState.ASK_TO_CHOOSE_REGION_TO_ADD);

        return regionService.handleInputMessage(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADD_REGION;
    }

}



