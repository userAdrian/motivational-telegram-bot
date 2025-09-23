package it.vrad.motivational.telegram.bot.infrastructure.web.controller;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(
        value = "/api/telegram",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public interface TelegramApi {

    @PostMapping("/webhook")
    ResponseEntity<Boolean> webhook(@RequestBody Update update) throws Exception;

}
