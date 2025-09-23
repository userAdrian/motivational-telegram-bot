package it.vrad.motivational.telegram.bot.core.facade.callback.base;

import it.vrad.motivational.telegram.bot.core.exception.UncheckedWrapperException;
import it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.core.functional.CheckedConsumer;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * Base facade service providing common callback-related utility methods for Telegram bot facades.
 */
@Slf4j
public abstract class BaseCallbackFacadeService {
    protected final TelegramService telegramService;

    protected BaseCallbackFacadeService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    /**
     * Extracts the {@link Message} from the given {@link IncomingCallbackContext} by updating the message sender
     * from the {@link CallbackQuery}, and answers the callback query to Telegram.
     * <p>
     * This method performs two actions:
     * <ul>
     *     <li>Extracts and updates the message sender from the callback query.
     *          <br>{@link CallbackUtility#updateMessageSenderFromCallback(CallbackQuery)}</li>
     *     <li>Sends an answer to the callback query using the {@link TelegramService}.</li>
     * </ul>
     *
     * @param incomingCallbackContext the context containing the callback query
     * @return the updated {@link Message} extracted from the callback query
     */
    protected Message extractMessageAndAnswerCallback(IncomingCallbackContext incomingCallbackContext) {
        CallbackQuery callbackQuery = incomingCallbackContext.getCallbackQuery();
        Message message = CallbackUtility.updateMessageSenderFromCallback(callbackQuery);

        telegramService.answerCallbackQuery(callbackQuery, MessageUtility.getChatId(message));

        return message;
    }

    /**
     * Processes a callback action for a given context and logs the start and end of the process.
     * <p>
     * This method extracts the {@link Message} from the {@link IncomingCallbackContext}, logs the start of the process,
     * executes the provided action (which may throw an exception), and logs the end of the process. Any exception thrown
     * by the action is wrapped in an {@link UncheckedWrapperException}.
     *
     * @param incomingCallbackContext the context containing the callback query
     * @param processName             a descriptive name for the process, used in log messages
     * @param action                  the action to perform on the extracted {@link Message}, which may throw an exception
     * @return always returns {@link Optional#empty()}
     * @throws UncheckedWrapperException if the action throws any exception
     */
    protected Optional<CallbackQueryDto> handleCallbackAction(
            IncomingCallbackContext incomingCallbackContext,
            String processName,
            CheckedConsumer<Message> action
    ) {
        Objects.requireNonNull(incomingCallbackContext);

        // Extract callback query and update message sender
        Message message = extractMessageAndAnswerCallback(incomingCallbackContext);

        Long chatId = MessageUtility.getChatId(message);
        log.info(FacadeUtility.STARTING_PROCESS_LOG_MESSAGE, processName, chatId);

        FacadeUtility.execWithWrap(message, action);

        log.info(FacadeUtility.FINISHED_PROCESS_LOG_MESSAGE, processName, chatId);
        return Optional.empty();
    }
}

