package it.vrad.motivational.telegram.bot.core.facade.message;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Facade service interface for info page message operations.
 */
public interface InfoPageMessageFacadeService {

    /**
     * Forwards the user to the info page.
     * <p>
     * This method is responsible for handling the navigation using buttons to the info page
     * </p>
     *
     * @param incomingMessageContext the context of the incoming message, containing user and message details
     */
    void forwardToInfoPage(IncomingMessageContext incomingMessageContext);

}
