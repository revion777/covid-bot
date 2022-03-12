package com.revion.covidbot.services.message.region;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.SendMessageBuilder;
import com.revion.covidbot.services.region.RegionService;
import com.revion.covidbot.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@Service
@Slf4j
public class AddRegionSendMessageService extends RegionSendMessageService {

    public AddRegionSendMessageService(RegionService regionService, UserService userService, UserDataCache userDataCache,
                                       SendMessageBuilder messageBuilder) {
        super(regionService, userDataCache, userService, messageBuilder);
    }

    @Override
    SendMessage generateSpecificReplyToUser(UserEntity user, RegionEntity region) {
        boolean res = user.getRegions().add(region);
        if (res) {
            return messageBuilder.getSuccessEmojiMessage(String.valueOf(user.getChatId()), "reply.region.added");
        }

        log.info(LogMessage.REGION_ALREADY_UPDATED, user.getId(), region.getTitle());
        return messageBuilder.getInfoReplyMessage(String.valueOf(user.getChatId()), "reply.region.alreadyAdded");
    }

}
