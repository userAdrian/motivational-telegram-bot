package it.vrad.motivational.telegram.bot.core.service.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

/**
 * Service interface for handling initial page callback actions.
 * <p>
 * Provides method to process callback queries for the initial page.
 */
public interface InitialPageCallbackService {

    /**
     * Processes a callback query for the initial page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@code null}
     */
    CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext);
}
