package com.revion.covidbot.services.region;

import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionEntity> findRegionByTitle(String title) {
        return regionRepository.findByTitle(title);
    }

    public void initSaving(List<RegionEntity> newRegionList) {
        List<RegionEntity> savedRegionList = findAllRegions();

        fillNewRegionsWithIds(savedRegionList, newRegionList);

        regionRepository.saveAll(newRegionList);
        log.info(LogMessage.REGIONS_SUCCESSFULLY_SAVED);
    }

    @Transactional(readOnly = true)
    public List<RegionEntity> findAllRegions() {
        return regionRepository.findAll();
    }

    private void fillNewRegionsWithIds(List<RegionEntity> savedRegionList, List<RegionEntity> newRegionList) {
        savedRegionList.forEach(savReg -> newRegionList.stream()
                .filter(newReg -> savReg.getCode().equals(newReg.getCode()))
                .findFirst()
                .ifPresent(newReg -> newReg.setId(savReg.getId())));
    }

    public void fillReplyTextWithRegionInfo(StringBuilder replyText, RegionEntity region) {
        replyText.append("*")
                .append(region.getTitle())
                .append("*:\n_Зараженных_: +")
                .append(region.getSickIncr())
                .append("\n_Умерших_: +")
                .append(region.getDiedIncr())
                .append("\n_Активных_: ")
                .append(region.getSick() -
                        region.getHealed() - region.getDied())
                .append("\n_Всего переболевших_: ")
                .append(region.getSick())
                .append("\n_Всего умерших_: ")
                .append(region.getDied())
                .append("\n\n");
    }

}

