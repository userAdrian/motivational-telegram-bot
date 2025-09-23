package it.vrad.motivational.telegram.bot.core.facade.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

import java.util.Optional;

/**
 * Facade service interface for handling info page callback actions.
 * <p>
 * Provides method to process callback queries for the info page.
 */
public interface InfoPageCallbackFacadeService {
    /**
     * Processes the callback query for the info page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@link Optional#empty()}
     */
    Optional<CallbackQueryDto> processCallbackQuery(IncomingCallbackContext incomingCallbackContext);
}
