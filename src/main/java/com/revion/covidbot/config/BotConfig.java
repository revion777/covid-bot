package com.revion.covidbot.config;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.objects.logging.LogMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import javax.annotation.PostConstruct;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Configuration
@ConfigurationProperties(prefix = "bot")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BotConfig {

    @Value("${bot.path}")
    String botPath;
    @Value("${bot.username}")
    String botUserName;
    @Value("${bot.token}")
    String botToken;

    @Bean
    public SetWebhook webhookInstance() {
        return SetWebhook.builder().url(botPath).build();
    }

    @Bean
    public CovidBot covidBot(SetWebhook webhookInstance) {
        CovidBot covidBot = new CovidBot(webhookInstance);
        covidBot.setBotPath(botPath);
        covidBot.setBotUsername(botUserName);
        covidBot.setBotToken(botToken);
        return covidBot;
    }

    @PostConstruct
    public void showInfo() {
        log.info(LogMessage.BOT_CONFIG, this);
    }

}
