package com.revion.covidbot;

import com.revion.covidbot.objects.logging.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class CovidBotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(CovidBotApplication.class, args);
	}

	@PostConstruct
	public void testBot() {
		String date = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy").format(new Date());
		log.info(LogMessage.APP_STARTS, date);
	}
}