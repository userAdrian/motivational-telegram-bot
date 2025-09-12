package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;

/**
 * Service interface for phrase message operations.
 */
public interface PhraseMessageService {

    /**
     * Processes the random phrase command sent by the user.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto processRandomPhraseCommand(IncomingMessageContext incomingMessageContext);

}
