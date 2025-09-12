package it.vrad.motivational.telegram.bot.core.service.callback.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.callback.AbstractCallbackService;
import it.vrad.motivational.telegram.bot.core.service.callback.InitialPageCallbackService;
import it.vrad.motivational.telegram.bot.core.service.message.InitialMessageService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link InitialPageCallbackService}
 * for handling initial page callback actions
 */
@Service
public class InitialPageCallbackServiceImpl extends AbstractCallbackService implements InitialPageCallbackService {
    private final InitialMessageService initialMessageService;
    private final UserService userService;

    public InitialPageCallbackServiceImpl(
            InitialMessageService initialMessageService,
            TelegramIntegrationApi telegramIntegrationApi, UserService userService
    ) {
        super(telegramIntegrationApi);
        this.initialMessageService = initialMessageService;
        this.userService = userService;
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

        // Find and validate the user from the database
        UserDto userFromDB = userService.findValidUser(MessageUtility.getUserId(message));

        // Generate the welcome message DTO for the user
        MessageDto messageDto = initialMessageService.generateWelcomeMessageDto(message.getFrom(), userFromDB);

        // Edit the message media to show the welcome message
        telegramIntegrationApi.editMessageMedia(TelegramApiRequestUtility.getEditMessageMediaPhotoRequest(message, messageDto));

        return null;
    }
}
