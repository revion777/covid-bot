package com.revion.covidbot.services.overall.builders;

import com.revion.covidbot.entities.OverallEntity;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.revion.covidbot.objects.CovidDataKeys.DIED;
import static com.revion.covidbot.objects.CovidDataKeys.HEALED;
import static com.revion.covidbot.objects.CovidDataKeys.SICK;
import static com.revion.covidbot.objects.ExtractorDocumentPath.*;
import static com.revion.covidbot.utils.CommonUtils.stringToNumber;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@Service
public class OverallBuilder {

    private static final long ONE_ROW_ID = 1L;
    private static final String SICK_CHANGE = "sickChange";
    private static final String HEALED_CHANGE = "healedChange";
    private static final String DIED_CHANGE = "diedChange";

    public OverallEntity buildFrom(Document doc) {
        String stateInfoTitle = getStateInfoTitle(doc);
        Element spreadOverviewSection = doc.getElementsByTag(OVERALL_START).first();
        String jsonData = Objects.requireNonNull(spreadOverviewSection).attr(OVERALL_TARGET);
        JSONObject jsonObj = new JSONObject(jsonData);

        return OverallEntity.builder()
                .id(ONE_ROW_ID)
                .title(stateInfoTitle)
                .sick(stringToNumber(jsonObj.getString(SICK)))
                .sickChange(stringToNumber(jsonObj.getString(SICK_CHANGE)))
                .healed(stringToNumber(jsonObj.getString(HEALED)))
                .healedChange(stringToNumber(jsonObj.getString(HEALED_CHANGE)))
                .died(stringToNumber(jsonObj.getString(DIED)))
                .diedChange(stringToNumber(jsonObj.getString(DIED_CHANGE)))
                .build();
    }

    private String getStateInfoTitle(Document doc) {
        return doc.getElementsByClass(TITLE_START).stream()
                .filter(element -> !element.getElementsMatchingText(TITLE_DESCRIPTION).isEmpty())
                .map(this::getStateInfoValueByTargetTag)
                .findFirst()
                .orElse(Strings.EMPTY);
    }

    private String getStateInfoValueByTargetTag(Element startElem) {
        return startElem.getElementsByTag(TITLE_TARGET).stream()
                .findFirst()
                .map(Element::text)
                .orElse(Strings.EMPTY);
    }

}
