package it.vrad.motivational.telegram.bot.infrastructure.web.controller;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/telegram")
public interface TelegramApi {

    @PostMapping("/webhook")
    boolean webhook(@RequestBody Update update);

}
