package com.revion.covidbot.services.extractor;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.services.message.show.statistic.ShowStatisticSendMessageService;
import com.revion.covidbot.services.overall.OverallService;
import com.revion.covidbot.services.overall.builders.OverallBuilder;
import com.revion.covidbot.services.region.RegionService;
import com.revion.covidbot.services.region.builders.RegionBuilder;
import com.revion.covidbot.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

import static com.revion.covidbot.objects.ExtractorDocumentPath.MAIN_URL;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ExtractorService {

    private final OverallBuilder overallBuilder;
    private final RegionBuilder regionBuilder;
    private final OverallService overallService;
    private final RegionService regionService;
    private final ShowStatisticSendMessageService showStatisticService;
    private final UserService userService;
    private final CovidBot covidBot;

    @Transactional
    public void startExtractorService() throws Exception {
        Document doc = Jsoup.connect(MAIN_URL).get();

        OverallEntity overall = overallBuilder.buildFrom(doc);
        overallService.save(overall);

        List<RegionEntity> regionList = regionBuilder.buildListFrom(doc);
        regionService.initSaving(regionList);

        userService.findAllUsers().forEach(this::sendMsgStatisticToUser);
    }

    private void sendMsgStatisticToUser(UserEntity savedUser) {
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
