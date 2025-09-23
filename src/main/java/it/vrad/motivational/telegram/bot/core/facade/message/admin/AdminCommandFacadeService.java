package it.vrad.motivational.telegram.bot.core.facade.message.admin;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;

import java.util.Optional;

/**
 * Facade service interface for handling admin commands.
 */
public interface AdminCommandFacadeService {
    /**
     * Processes the command to load file phrases, sends instructions, and updates step detail.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@link Optional#empty()}
     */
    Optional<MessageDto> processLoadFilePhrasesCommand(IncomingMessageContext incomingMessageContext);

    /**
     * Processes the first step of loading file phrases: receives the file, parses phrases, saves them, and sends confirmation.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@link Optional#empty()}
     */
    Optional<MessageDto> processLoadFilePhrasesCommandStepOne(IncomingMessageContext incomingMessageContext);
}
