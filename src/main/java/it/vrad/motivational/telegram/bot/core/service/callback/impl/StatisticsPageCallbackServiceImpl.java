package it.vrad.motivational.telegram.bot.core.service.callback.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.callback.AbstractCallbackService;
import it.vrad.motivational.telegram.bot.core.service.callback.StatisticsPageCallbackService;
import it.vrad.motivational.telegram.bot.core.service.message.StatisticsPageMessageService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link StatisticsPageCallbackService}
 * for handling statistics page callback actions.
 */
@Service
public class StatisticsPageCallbackServiceImpl extends AbstractCallbackService implements StatisticsPageCallbackService {
    private final StatisticsPageMessageService statisticsPageMessageService;

    public StatisticsPageCallbackServiceImpl(
            StatisticsPageMessageService statisticsPageMessageService,
            TelegramIntegrationApi telegramIntegrationApi
    ) {
        super(telegramIntegrationApi);
        this.statisticsPageMessageService = statisticsPageMessageService;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingCallbackContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext) {
        Objects.requireNonNull(incomingCallbackContext);

        // Extract callback query and update message sender
        CallbackQuery callbackQuery = incomingCallbackContext.getCallbackQuery();
        Message message = CallbackUtility.updateMessageSenderFromCallback(callbackQuery);

        // Answer the callback query to Telegram
        answerCallbackQuery(callbackQuery, MessageUtility.getChatId(message));

        // Forward to statistics page
        statisticsPageMessageService.forwardToStatisticsPage(ObjectsFactory.buildMessageParameterDto(message));

        return null;
    }
}
