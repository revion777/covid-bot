package com.revion.covidbot.controllers;

import com.revion.covidbot.botapi.CovidBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class WebHookController {

    private final CovidBot covidBot;

    @PostMapping(value = "/")
    public BotApiMethod<Message> onUpdateReceived(@RequestBody Update update){
        return covidBot.onWebhookUpdateReceived(update);
    }

}
