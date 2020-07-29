package com.revion.covidbot.controllers;

import com.revion.covidbot.botapi.CovidBot;
import com.revion.covidbot.objects.logging.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@RestController
@Slf4j
public class WebHookController {

    private final CovidBot covidBot;

    public WebHookController(CovidBot covidBot) {
        this.covidBot = covidBot;
    }

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        return covidBot.onWebhookUpdateReceived(update);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String pingBotMainPage() {
        log.info(LogMessage.BOT_PING, HttpStatus.OK.toString());
        return HttpStatus.OK.toString();
    }
}
