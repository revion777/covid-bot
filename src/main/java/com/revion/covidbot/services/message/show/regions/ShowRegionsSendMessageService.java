package com.revion.covidbot.services.message.show.regions;


import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.SendMessageBuilder;
import com.revion.covidbot.services.region.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
public class ShowRegionsSendMessageService {

    private final RegionService regionService;
    private final UserDataCache userDataCache;
    private final SendMessageBuilder messageBuilder;

    public SendMessage init(Message message) {
        StringBuilder replyText = new StringBuilder("*Наименования субъектов РФ:*\n");

        regionService.findAllRegions().stream()
                .sorted(Comparator.comparing(RegionEntity::getTitle))
                .forEach(region -> replyText
                        .append("_")
                        .append(region.getTitle())
                        .append("_\n"));

        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

        return messageBuilder.getPlainTextMessage(String.valueOf(message.getChatId()), replyText.toString());
    }
}



