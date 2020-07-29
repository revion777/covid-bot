package com.revion.covidbot;

import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.objects.DocumentStaticPath;
import com.revion.covidbot.services.ExtractorService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class CovidBotApplicationTests {

    @Autowired
    private ExtractorService extractorService;

    private Document doc;

    @Test
    void test() throws IOException {
        testDoc();
        testOverallInfo();
        testRegionStatList();
    }

    private void testDoc() throws IOException {
        doc = Jsoup.connect(DocumentStaticPath.MAIN_URL).get();
        Assertions.assertNotNull(doc);
    }

    private void testOverallInfo() {
        OverallEntity overallInfo = extractorService.getOverall(doc);
        Assertions.assertNotNull(overallInfo);
        System.out.println(overallInfo.toString());
        Assertions.assertNotNull(overallInfo.getTitle());
    }

    private void testRegionStatList() {
        List<RegionEntity> regionStatList = extractorService.getRegions(doc);
        Assertions.assertFalse(regionStatList.isEmpty());
    }
}
