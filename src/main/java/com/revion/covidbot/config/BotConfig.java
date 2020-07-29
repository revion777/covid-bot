package com.revion.covidbot.config;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.objects.logging.LogMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Getter
@Setter
@ToString
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

//    @Value("${bot.proxyType}")
//    DefaultBotOptions.ProxyType proxyType;
//    @Value("${bot.proxyHost}")
//    String proxyHost;
//    @Value("${bot.proxyPort}")
//    int proxyPort;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CovidBot getCovidBot() {
//        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
//        options.setProxyHost(proxyHost);
//        options.setProxyPort(proxyPort);
//        options.setProxyType(proxyType);
//        CovidBot covidBot = new CovidBot(botOptions);

        CovidBot covidBot = new CovidBot();
        covidBot.setBotPath(botPath);
        covidBot.setBotUsername(botUserName);
        covidBot.setBotToken(botToken);
        return covidBot;
    }

    @PostConstruct
    public void showInfo() {
//        log.info(LogMessage.BOT_OPTIONS, proxyHost, proxyPort, proxyType);
        log.info(LogMessage.BOT_CONFIG, toString());
    }
}
