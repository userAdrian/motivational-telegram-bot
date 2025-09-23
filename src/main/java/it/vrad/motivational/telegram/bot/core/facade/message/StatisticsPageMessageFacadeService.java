package it.vrad.motivational.telegram.bot.core.facade.message;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Service interface for statistics page message operations.
 */
public interface StatisticsPageMessageFacadeService {

    /**
     * Forwards the user to the statistics page.
     * <p>
     * This method is responsible for handling the navigation with buttons to the statistics page
     * </p>
     *
     * @param incomingMessageContext the context of the incoming message, containing user and message details
     */
    void forwardToStatisticsPage(IncomingMessageContext incomingMessageContext);

}
