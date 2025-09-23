package it.vrad.motivational.telegram.bot.core.facade.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

import java.util.Optional;

/**
 * Facade service interface for handling admin page callback actions.
 * <p>
 * Provides methods to process admin callback queries
 */
public interface AdminPageCallbackFacadeService {

    /**
     * Processes the callback query for the admin page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@link Optional#empty()}
     */
    Optional<CallbackQueryDto> processCallbackQuery(IncomingCallbackContext incomingCallbackContext);

    /**
     * Processes the callback for the "Load Phrases File" button on the admin page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@link Optional#empty()}
     */
    Optional<CallbackQueryDto> processLoadPhrasesFileButton(IncomingCallbackContext incomingCallbackContext);
}
