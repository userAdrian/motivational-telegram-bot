package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;

/**
 * Service interface for statistics page message operations.
 */
public interface StatisticsPageMessageService {

    /**
     * Forwards the user to the statistics page.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto forwardToStatisticsPage(IncomingMessageContext incomingMessageContext);
}
