package it.vrad.motivational.telegram.bot.infrastructure.web.controller.impl;


import it.vrad.motivational.telegram.bot.infrastructure.web.controller.TelegramApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import it.vrad.motivational.telegram.bot.core.dispatch.UpdateDispatcher;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramApiImpl implements TelegramApi {

    private final UpdateDispatcher updateHandler;

    public TelegramApiImpl(UpdateDispatcher updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public boolean webhook(Update update) {
        updateHandler.dispatch(update);

        return true;
    }

}
