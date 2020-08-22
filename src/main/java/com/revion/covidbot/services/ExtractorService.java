package com.revion.covidbot.services;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.objects.DocumentStaticPath;
import com.revion.covidbot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
@Slf4j
public class ExtractorService {

    private final CovidBot covidBot;
    private final OverallService overallService;
    private final RegionService regionService;
    private final ShowStatisticService showStatisticService;

    public ExtractorService(CovidBot covidBot,
                            OverallService overallService,
                            RegionService regionService, ShowStatisticService showStatisticService) {
        this.covidBot = covidBot;
        this.overallService = overallService;
        this.regionService = regionService;
        this.showStatisticService = showStatisticService;
    }

    public void startExtractorService() throws Exception {
        Document doc = Jsoup.connect(DocumentStaticPath.MAIN_URL).get();

        OverallEntity overall = getOverall(doc);
        overallService.save(overall);

        List<RegionEntity> regionList = getRegions(doc);
        regionService.initSaving(regionList);

        showStatisticService.sendMsgStatisticToAllUsers(covidBot);
    }

    public OverallEntity getOverall(Document doc) {
        String stateInfoTitle = getStateInfoTitle(doc);

        Element spreadOverviewSection = doc.getElementsByTag(DocumentStaticPath.OVERALL_START).first();
        String jsonData = spreadOverviewSection.attr(DocumentStaticPath.OVERALL_TARGET);
        JSONObject jsonObj = new JSONObject(jsonData);

        return OverallEntity.builder()
                .id(1)
                .title(stateInfoTitle)
                .sick(CommonUtils.stringToNumber(jsonObj.getString("sick")))
                .sickChange(CommonUtils.stringToNumber(jsonObj.getString("sickChange")))
                .healed(CommonUtils.stringToNumber(jsonObj.getString("healed")))
                .healedChange(CommonUtils.stringToNumber(jsonObj.getString("healedChange")))
                .died(CommonUtils.stringToNumber(jsonObj.getString("died")))
                .diedChange(CommonUtils.stringToNumber(jsonObj.getString("diedChange")))
                .build();
    }

    public String getStateInfoTitle(Document doc) {
        Elements startElements = doc.getElementsByClass(DocumentStaticPath.TITLE_START);

        Optional<String> infoState = startElements.stream()
                .map(this::getStateInfoValueByTargetTag)
                .findFirst();

        return infoState.orElse(null);
    }

    private String getStateInfoValueByTargetTag(Element startElem) {
        Optional<Element> targetElem = startElem.getElementsByTag(DocumentStaticPath.TITLE_TARGET).stream()
                .findFirst();
        return targetElem.map(Element::text).orElse(null);
    }

    public List<RegionEntity> getRegions(Document doc) {
        Element spreadOverviewSection = doc.getElementsByTag(DocumentStaticPath.STAT_START).first();
        String jsonData = spreadOverviewSection.attr(DocumentStaticPath.STAT_TARGET);
        JSONArray jsonArray = new JSONArray(jsonData);
        List<RegionEntity> regions = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            RegionEntity covidStat = RegionEntity.builder()
                    .code(jsonObj.getString("code"))
                    .title(jsonObj.getString("title"))
                    .sick(CommonUtils.stringToNumber(jsonObj.getInt("sick")))
                    .sickIncr(CommonUtils.stringToNumber(jsonObj.getInt("sick_incr")))
                    .healed(CommonUtils.stringToNumber(jsonObj.getInt("healed")))
                    .healedIncr(CommonUtils.stringToNumber(jsonObj.getInt("healed_incr")))
                    .died(CommonUtils.stringToNumber(jsonObj.getInt("died")))
                    .diedIncr(CommonUtils.stringToNumber(jsonObj.getInt("died_incr")))
                    .build();
            regions.add(covidStat);
        }

        return regions;
    }
}
