package com.revion.covidbot.services;

import com.revion.covidbot.entities.OverallEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.OverallRepo;
import com.revion.covidbot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
@Slf4j
public class OverallService {

    private final OverallRepo overallRepo;

    public OverallService(OverallRepo overallRepo) {
        this.overallRepo = overallRepo;
    }

    @Transactional(readOnly = true)
    public OverallEntity findOverall() {
        return overallRepo.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void save(OverallEntity overall) {
        log.info(LogMessage.OVERALL_INFO_SUCCESSFULLY_SAVED);
        overallRepo.save(overall);
    }

    public StringBuilder getOverallInfo(OverallEntity overall) {
        return new StringBuilder()
                .append(overall.getTitle())
                .append("\n\n*В России:*\n_Зараженных_: +")
                .append(CommonUtils.getDecimalFormatter().format(overall.getSickChange()))
                .append("\n_Умерших_: +")
                .append(CommonUtils.getDecimalFormatter().format(overall.getDiedChange()))
                .append("\n_Активных_: ")
                .append(CommonUtils.getDecimalFormatter().format(overall.getSick() -
                        overall.getHealed() - overall.getDied()))
                .append("\n_Всего переболевших_: ")
                .append(CommonUtils.getDecimalFormatter().format(overall.getSick()))
                .append("\n_Всего умерших_: ")
                .append(CommonUtils.getDecimalFormatter().format(overall.getDied()))
                .append("\n\n");
    }
}
