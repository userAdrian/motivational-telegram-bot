package it.vrad.motivational.telegram.bot.core.facade.util;


import it.vrad.motivational.telegram.bot.core.exception.UncheckedWrapperException;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.functional.CheckedConsumer;
import it.vrad.motivational.telegram.bot.core.functional.CheckedFunction;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for executing actions with unified exception handling and process logging for Telegram bot facades.
 * <p>
 * This class is intended to be used by facade services to ensure consistent logging and error handling for all
 * Telegram bot command and callback processes.
 * </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FacadeUtility {

    public static final String STARTING_PROCESS_LOG_MESSAGE = "Starting process of {} for chatId={}";
    public static final String FINISHED_PROCESS_LOG_MESSAGE = "Finished process of {} for chatId={}";

    /**
     * Executes the given action with unified exception handling and process logging.
     * <p>
     * Logs the start and end of the process (including the process name and chatId), and wraps any thrown exception
     * in an {@link UncheckedWrapperException}.
     * </p>
     * <p>
     * See also {@link FacadeUtility#execWithWrap(Object, CheckedFunction)}
     * </p>
     *
     * @param incomingMessageContext the context containing the incoming message
     * @param processName            a descriptive name for the process, used in log messages
     * @param action                 the action to perform, which returns an {@link Optional} result and may throw an exception
     * @return the result of the action
     * @throws UncheckedWrapperException if the action throws any exception
     */
    public static Optional<MessageDto> execWithWrap(IncomingMessageContext incomingMessageContext,
                                                    String processName,
                                                    CheckedFunction<IncomingMessageContext, Optional<MessageDto>> action) {
        Objects.requireNonNull(incomingMessageContext);

        Message message = incomingMessageContext.getSentMessage();
        Long chatId = MessageUtility.getChatId(message);

        logStartProcess(processName, chatId);

        Optional<MessageDto> result = execWithWrap(incomingMessageContext, action);

        logFinishedProcess(processName, chatId);
        return result;

    }

    /**
     * Executes the given consumer action with unified exception handling and process logging.
     * <p>
     * Logs the start and end of the process (including the process name and chatId), and wraps any thrown exception
     * in an {@link UncheckedWrapperException}.
     * </p>
     * <p>
     * See also {@link FacadeUtility#execWithWrap(Object, CheckedConsumer)}
     * </p>
     *
     * @param incomingMessageContext the context containing the incoming message
     * @param processName            a descriptive name for the process, used in log messages
     * @param action                 the consumer action to perform, which may throw an exception
     * @throws UncheckedWrapperException if the action throws any exception
     */
    public static void execWithWrap(IncomingMessageContext incomingMessageContext,
                                    String processName,
                                    CheckedConsumer<IncomingMessageContext> action) {
        Objects.requireNonNull(incomingMessageContext);

        Message message = incomingMessageContext.getSentMessage();
        Long chatId = MessageUtility.getChatId(message);

        logStartProcess(processName, chatId);

        execWithWrap(incomingMessageContext, action);

        logFinishedProcess(processName, chatId);

    }

    /**
     * Executes the given consumer action with unified exception handling.
     * <p>
     * Wraps any thrown exception in an {@link UncheckedWrapperException}.
     * </p>
     *
     * @param param  the parameter to pass to the consumer action
     * @param action the consumer action to perform, which may throw an exception
     * @param <T>    the type of the parameter
     * @throws UncheckedWrapperException if the action throws any exception
     */
    public static <T> void execWithWrap(T param, CheckedConsumer<T> action) {
        try {
            action.accept(param);
        } catch (Exception ex) {
            throw new UncheckedWrapperException(ex);
        }
    }

    /**
     * Executes the given function action with unified exception handling.
     * <p>
     * Wraps any thrown exception in an {@link UncheckedWrapperException}.
     * </p>
     *
     * @param param  the parameter to pass to the function action
     * @param action the function action to perform, which may throw an exception
     * @param <T>    the type of the parameter
     * @param <R>    the return type of the function
     * @return the result of the function action
     * @throws UncheckedWrapperException if the action throws any exception
     */
    public static <T, R> R execWithWrap(T param, CheckedFunction<T, R> action) {
        try {
            return action.accept(param);
        } catch (Exception ex) {
            throw new UncheckedWrapperException(ex);
        }
    }

    private static void logStartProcess(String processName, Long chatId) {
        log.info(STARTING_PROCESS_LOG_MESSAGE, processName, chatId);
    }

    private static void logFinishedProcess(String processName, Long chatId) {
        log.info(FINISHED_PROCESS_LOG_MESSAGE, processName, chatId);
    }
}
