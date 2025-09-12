package it.vrad.motivational.telegram.bot.core.model.context;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncomingCallbackContext {
    private CallbackQuery callbackQuery;
}
