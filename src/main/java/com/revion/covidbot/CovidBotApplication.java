package com.revion.covidbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@SpringBootApplication
@EnableScheduling
public class CovidBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidBotApplication.class, args);
    }

}