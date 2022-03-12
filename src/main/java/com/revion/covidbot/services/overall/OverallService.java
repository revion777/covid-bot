package com.revion.covidbot.services.overall;

import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.OverallRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class OverallService {

    private static final long ONE_ROW_ID = 1L;

    private final OverallRepository overallRepository;

    public StringBuilder getOverallInfo() {
        OverallEntity overall = overallRepository.findById(ONE_ROW_ID).orElseThrow();

        return new StringBuilder()
                .append(overall.getTitle())
                .append("\n\n*В России:*\n_Зараженных_: +")
                .append(overall.getSickChange())
                .append("\n_Умерших_: +")
                .append(overall.getDiedChange())
                .append("\n_Активных_: ")
                .append(overall.getSick() - overall.getHealed() - overall.getDied())
                .append("\n_Всего переболевших_: ")
                .append(overall.getSick())
                .append("\n_Всего умерших_: ")
                .append(overall.getDied())
                .append("\n\n");
    }

    public void save(OverallEntity overall) {
        overallRepository.save(overall);
        log.info(LogMessage.OVERALL_INFO_SUCCESSFULLY_SAVED);
    }

}
