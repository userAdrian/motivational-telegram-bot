package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Service interface for info page message operations.
 * <p>
 * Provides method for forwarding to the info page.
 */
public interface InfoPageMessageService {

    /**
     * Forwards the user to the info page.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto forwardToInfoPage(IncomingMessageContext incomingMessageContext);
}
