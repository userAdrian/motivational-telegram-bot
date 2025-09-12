package it.vrad.motivational.telegram.bot.core.service.callback;

import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;

/**
 * Service interface for handling admin page callback actions.
 * <p>
 * Provides methods to process admin callback queries
 */
public interface AdminPageCallbackService {

    /**
     * Processes a callback query for the admin page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@code null}
     */
    CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext);

    /**
     * Processes the callback for the "Load Phrases File" button on the admin page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@code null}
     */
    CallbackQueryDto processLoadPhrasesFileButton(IncomingCallbackContext incomingCallbackContext);
}
