package com.revion.covidbot.services.region.builders;

import com.revion.covidbot.entities.RegionEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.revion.covidbot.objects.CovidDataKeys.CODE;
import static com.revion.covidbot.objects.CovidDataKeys.DIED;
import static com.revion.covidbot.objects.CovidDataKeys.DIED_INCR;
import static com.revion.covidbot.objects.CovidDataKeys.HEALED;
import static com.revion.covidbot.objects.CovidDataKeys.HEALED_INCR;
import static com.revion.covidbot.objects.CovidDataKeys.SICK;
import static com.revion.covidbot.objects.CovidDataKeys.SICK_INCR;
import static com.revion.covidbot.objects.CovidDataKeys.TITLE;
import static com.revion.covidbot.objects.ExtractorDocumentPath.STAT_START;
import static com.revion.covidbot.objects.ExtractorDocumentPath.STAT_TARGET;
import static com.revion.covidbot.utils.CommonUtils.stringToNumber;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@Service
public class RegionBuilder {

    public List<RegionEntity> buildListFrom(Document doc) {
        Element spreadOverviewSection = doc.getElementsByTag(STAT_START).first();
        String jsonData = Objects.requireNonNull(spreadOverviewSection).attr(STAT_TARGET);
        JSONArray jsonArray = new JSONArray(jsonData);

        List<RegionEntity> regions = new ArrayList<>();
        for (Object jsonObj : jsonArray) {
            RegionEntity regionEntity = buildFrom((JSONObject) jsonObj);
            regions.add(regionEntity);
        }
        return regions;
    }

    private RegionEntity buildFrom(JSONObject jsonObj) {
        return RegionEntity.builder()
                .code(jsonObj.getString(CODE))
                .title(jsonObj.getString(TITLE))
                .sick(stringToNumber(jsonObj.getInt(SICK)))
                .sickIncr(stringToNumber(jsonObj.getInt(SICK_INCR)))
                .healed(stringToNumber(jsonObj.getInt(HEALED)))
                .healedIncr(stringToNumber(jsonObj.getInt(HEALED_INCR)))
                .died(stringToNumber(jsonObj.getInt(DIED)))
                .diedIncr(stringToNumber(jsonObj.getInt(DIED_INCR)))
                .build();
    }

}
