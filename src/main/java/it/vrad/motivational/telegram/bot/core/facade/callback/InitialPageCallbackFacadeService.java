package it.vrad.motivational.telegram.bot.core.facade.callback;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;

import java.util.Optional;

/**
 * Facade service interface for handling initial page callback actions.
 * <p>
 * Provides method to process callback queries for the initial page.
 */
public interface InitialPageCallbackFacadeService {

    /**
     * Processes the callback query for the initial page.
     *
     * @param incomingCallbackContext the context of the incoming callback
     * @return {@link Optional#empty()}
     */
    Optional<CallbackQueryDto> processCallbackQuery(IncomingCallbackContext incomingCallbackContext);
}
