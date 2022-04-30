package com.revion.covidbot.services.extractor;

import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.services.overall.OverallService;
import com.revion.covidbot.services.overall.builders.OverallBuilder;
import com.revion.covidbot.services.region.RegionService;
import com.revion.covidbot.services.region.builders.RegionBuilder;
import com.revion.covidbot.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.revion.covidbot.objects.ExtractorDocumentPath.MAIN_URL;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
public class ExtractorService {

    private final OverallBuilder overallBuilder;
    private final RegionBuilder regionBuilder;
    private final OverallService overallService;
    private final RegionService regionService;
    private final UserService userService;
    private final MessageSender messageSender;

    public void startExtractorService() throws Exception {
        Document doc = Jsoup.connect(MAIN_URL).get();

        OverallEntity overall = overallBuilder.buildFrom(doc);
        overallService.save(overall);

        List<RegionEntity> regionList = regionBuilder.buildListFrom(doc);
        regionService.initSaving(regionList);

        messageSender.sendMsgStatisticToUsers();
    }

}
