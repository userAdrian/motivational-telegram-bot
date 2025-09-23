package it.vrad.motivational.telegram.bot.core.facade.message;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;

import java.util.Optional;

/**
 * Facade service interface for phrase message operations.
 */
public interface PhraseMessageFacadeService {

    /**
     * Processes the random phrase command sent by the user.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return always an {@link Optional#empty()}
     */
    Optional<MessageDto> processRandomPhraseCommand(IncomingMessageContext incomingMessageContext);

}
