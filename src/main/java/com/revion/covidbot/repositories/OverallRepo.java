package com.revion.covidbot.repositories;

import com.revion.covidbot.entities.OverallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
public interface OverallRepo extends JpaRepository<OverallEntity, Long> {

}
