package com.revion.covidbot.repositories;

import com.revion.covidbot.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
