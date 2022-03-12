package com.revion.covidbot.repositories;

import com.revion.covidbot.entities.OverallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public interface OverallRepository extends JpaRepository<OverallEntity, Long> {

}
