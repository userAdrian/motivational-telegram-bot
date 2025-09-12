package it.vrad.motivational.telegram.bot.core.service.callback.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.callback.AbstractCallbackService;
import it.vrad.motivational.telegram.bot.core.service.callback.InfoPageCallbackService;
import it.vrad.motivational.telegram.bot.core.service.message.InfoPageMessageService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link InfoPageCallbackService}
 * for handling info page callback actions.
 */
@Service
public class InfoPageCallbackServiceImpl extends AbstractCallbackService implements InfoPageCallbackService {
    private final InfoPageMessageService infoPageMessageService;

    public InfoPageCallbackServiceImpl(InfoPageMessageService infoPageMessageService, TelegramIntegrationApi telegramIntegrationApi) {
        super(telegramIntegrationApi);
        this.infoPageMessageService = infoPageMessageService;
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

        // Forward to info page
        infoPageMessageService.forwardToInfoPage(ObjectsFactory.buildMessageParameterDto(message));

        return null;
    }
}
