package com.revion.covidbot.controllers;

import com.revion.covidbot.objects.logging.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maxim Negodyuk created on 23.02.2021
 */
@RestController
@Slf4j
public class PingController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String pingBotMainPage() {
        log.info(LogMessage.BOT_PING, HttpStatus.OK);
        return HttpStatus.OK.toString();
    }

}
