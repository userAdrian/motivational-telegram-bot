package it.vrad.motivational.telegram.bot.core.service.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

/**
 * Service interface for handling info page callback actions.
 * <p>
 * Provides method to process callback queries for the info page.
 */
public interface InfoPageCallbackService {
    /**
     * Processes a callback query for the info page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@code null}
     */
    CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext);
}
