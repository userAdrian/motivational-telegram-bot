package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Service interface for admin message operations.
 * <p>
 * Provides methods for forwarding to the admin page and processing file phrase commands.
 */
public interface AdminMessageService {

    /**
     * Forwards the user to the admin page.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto forwardToAdminPage(IncomingMessageContext incomingMessageContext);

    /**
     * Processes the command to load file phrases, sends instructions, and updates step detail.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto processLoadFilePhrasesCommand(IncomingMessageContext incomingMessageContext);

    /**
     * Processes the first step of loading file phrases: receives the file, parses phrases, saves them, and sends confirmation.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto processLoadFilePhrasesCommandStepOne(IncomingMessageContext incomingMessageContext);
}
