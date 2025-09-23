package it.vrad.motivational.telegram.bot.infrastructure.web.controller.impl;


import it.vrad.motivational.telegram.bot.core.dispatch.UpdateDispatcher;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.infrastructure.web.controller.TelegramApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramApiImpl implements TelegramApi {

    private final UpdateDispatcher updateHandler;

    public TelegramApiImpl(UpdateDispatcher updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public ResponseEntity<Boolean> webhook(Update update) throws Exception {
        try {
            updateHandler.dispatch(update);
        } catch (Exception ex) {
            throw ExceptionUtility.unwrapException(ex);
        }
        return ResponseEntity.ok(true);
    }

}
