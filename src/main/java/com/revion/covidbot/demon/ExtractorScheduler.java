package com.revion.covidbot.demon;

import com.revion.covidbot.services.extractor.ExtractorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class ExtractorScheduler {

    private final ExtractorService extractorService;

    @Scheduled(cron = ("${extractor.period}"))
    public void startExtractorJob() throws Exception {
        extractorService.startExtractorService();
    }
}
