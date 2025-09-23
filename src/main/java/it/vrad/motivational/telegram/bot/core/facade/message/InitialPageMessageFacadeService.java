package it.vrad.motivational.telegram.bot.core.facade.message;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;

import java.util.Optional;

/**
 * Facade service interface for initial page message operations.
 */
public interface InitialPageMessageFacadeService {

    /**
     * Processes the initial message sent by the user (corresponding to {@link CommandConstants.Initial})
     * and sends a welcome photo message.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return always an {@link Optional#empty()}
     */
    Optional<MessageDto> processInitialMessage(IncomingMessageContext incomingMessageContext);

    /**
     * Forwards the user to the initial page.
     * <p>
     * This method is responsible for handling the navigation using buttons to the initial page
     * </p>
     *
     * @param incomingMessageContext the context of the incoming message
     */
    void forwardToInitialPage(IncomingMessageContext incomingMessageContext);

}
