package it.vrad.motivational.telegram.bot.core.facade.message.admin;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Facade service interface for admin message operations.
 */
public interface AdminPageMessageFacadeService {

    /**
     * Forwards the user to the admin page.
     * <p>
     * This method is responsible for handling the navigation or redirection to the admin page,
     * </p>
     *
     * @param incomingMessageContext the context of the incoming message, containing user and message details
     */
    void forwardToAdminPage(IncomingMessageContext incomingMessageContext);

}
