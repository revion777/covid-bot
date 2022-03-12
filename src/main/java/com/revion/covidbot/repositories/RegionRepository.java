package com.revion.covidbot.repositories;

import com.revion.covidbot.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    @Query("SELECT reg FROM RegionEntity reg WHERE LOWER(reg.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<RegionEntity> findByTitle(String title);
}
