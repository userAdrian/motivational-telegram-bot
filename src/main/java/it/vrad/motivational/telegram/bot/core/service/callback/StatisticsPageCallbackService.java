package it.vrad.motivational.telegram.bot.core.service.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

/**
 * Service interface for handling statistics page callback actions.
 * <p>
 * Provides method to process callback queries for the statistics page.
 */
public interface StatisticsPageCallbackService {
    /**
     * Processes a callback query for the statistics page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@code null}
     */
    CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext);
}
