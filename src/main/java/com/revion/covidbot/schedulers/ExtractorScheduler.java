package com.revion.covidbot.schedulers;

import com.revion.covidbot.services.ExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class ExtractorScheduler {

    private final ExtractorService extractorService;

    public ExtractorScheduler(ExtractorService extractorService) {
        this.extractorService = extractorService;
    }

    @Scheduled(cron = ("${extractor.period}"))
    public void startExtractorJob() throws Exception {
        extractorService.startExtractorService();
    }
}
