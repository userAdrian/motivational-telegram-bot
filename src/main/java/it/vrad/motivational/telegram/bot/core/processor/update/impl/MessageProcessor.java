package it.vrad.motivational.telegram.bot.core.processor.update.impl;

import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.MessageActionFunction;
import it.vrad.motivational.telegram.bot.core.processor.update.UpdateProcessor;
import it.vrad.motivational.telegram.bot.core.processor.util.ExceptionProcessorUtility;
import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.core.exception.UnrecognizedCommandException;
import it.vrad.motivational.telegram.bot.infrastructure.cache.model.StepDetail;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import it.vrad.motivational.telegram.bot.infrastructure.cache.CacheService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;

import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Implementation of {@link UpdateProcessor} for handling message updates.
 * Determines the appropriate action based on the message text and step details.
 */
@Component(UpdateProcessorType.MESSAGE_PROCESSOR_BEAN_NAME)
public class MessageProcessor implements UpdateProcessor {

    private final Map<String, List<MessageActionFunction>> messageActionMap;
    private final CacheService cacheService;

    public MessageProcessor(@Qualifier("messageActionMap") Map<String, List<MessageActionFunction>> messageActionMap,
                            CacheService cacheService) {
        this.messageActionMap = messageActionMap;
        this.cacheService = cacheService;
    }

    /**
     * Processes the message update, determines the correct action function,
     * and applies it to the message parameter DTO.
     *
     * @param update the update to process
     */
    @Override
    public void accept(@NonNull Update update) {
        Objects.requireNonNull(update);
        Message message = Objects.requireNonNull(update.getMessage());
        Long chatId = MessageUtility.getChatId(message);

        try {
            // Get the appropriate action function for the message and execute it
            getMessageActionFunction(message.getText(), chatId).apply(ObjectsFactory.buildMessageParameterDto(message));
        } catch (Exception ex) {
            // Handle exceptions in a context-aware manner
            ExceptionProcessorUtility.handleUpdateProcessorException(ex, chatId);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a predicate that matches updates containing a message.
     */
    @Override
    public Predicate<Update> getProcessorFinder() {
        return (update) -> update.getMessage() != null;
    }

    /**
     * Determines the appropriate {@link MessageActionFunction} to use based on the message text and step detail.
     *
     * @param text   the message text
     * @param chatId the chat ID
     * @return the selected {@link MessageActionFunction}
     * @throws UnrecognizedCommandException if the command is not recognized
     */
    private MessageActionFunction getMessageActionFunction(String text, Long chatId) {
        StepDetail stepDetail = cacheService.getStepDetail(chatId);

        // If it's a command, get the first action for the command
        if (MessageUtility.isACommand(text)) {
            List<MessageActionFunction> stepList = messageActionMap.get(text);
            if (CollectionUtils.isEmpty(stepList)) {
                throw new UnrecognizedCommandException(chatId, text);
            }
            // If a process is already started, remove the step detail
            if (stepDetail.isThereAProcessStarted()) {
                cacheService.removeStepDetail(chatId);
            }
            return stepList.getFirst();
        }

        // If a process is started, use the step's command and step index
        if (stepDetail.isThereAProcessStarted()) {
            List<MessageActionFunction> stepList = messageActionMap.get(stepDetail.getCommand());
            return stepList.get(stepDetail.getStep());
        }

        // Default: use the initial command's first action
        return messageActionMap.get(CommandConstants.Initial.TEXT).getFirst();
    }
}
