package com.revion.covidbot.repositories;

import com.revion.covidbot.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
public interface RegionRepo extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByTitle(String title);
}
