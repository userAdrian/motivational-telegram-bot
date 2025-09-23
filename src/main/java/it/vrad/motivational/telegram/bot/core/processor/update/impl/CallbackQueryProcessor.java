package it.vrad.motivational.telegram.bot.core.processor.update.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.CallbackActionFunction;
import it.vrad.motivational.telegram.bot.core.processor.update.UpdateProcessor;
import it.vrad.motivational.telegram.bot.core.processor.util.ExceptionProcessorUtility;
import it.vrad.motivational.telegram.bot.core.model.constants.CallbackConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.core.model.CallbackDataDetails;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;

import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Implementation of {@link UpdateProcessor} for handling callback queries.
 * Processes callback data, determines the appropriate action, and executes it.
 */
@Slf4j
@Component(UpdateProcessorType.CALLBACK_QUERY_PROCESSOR_BEAN_NAME)
public class CallbackQueryProcessor implements UpdateProcessor {
    private final Map<String, List<CallbackActionFunction>> callbackActionMap;
    private final ObjectMapper objectMapper;

    public CallbackQueryProcessor(Map<String, List<CallbackActionFunction>> callbackActionMap, ObjectMapper objectMapper) {
        this.callbackActionMap = callbackActionMap;
        this.objectMapper = objectMapper;
    }

    /**
     * Processes the callback query from the update, extracts data details,
     * finds the corresponding action, and executes it.
     *
     * @param update the update to process
     */
    @Override
    public void accept(Update update) {
        Objects.requireNonNull(update);
        CallbackQuery callbackQuery = update.getCallbackQuery();

        try {
            // Extract callback data details from the query
            CallbackDataDetails dataDetails = extractDataDetails(callbackQuery.getData());

            // Update the callback query data with the extracted data.
            // callbackQuery.getData() != dataDetails.getData() only for DATA_REFERENCE_PREFIX case
            callbackQuery.setData(dataDetails.getData());

            // Validate and retrieve the list of actions
            List<CallbackActionFunction> actions = getValidatedActions(dataDetails);

            // Execute the action for the current step
            executeAction(actions, dataDetails.getStepIndex(), callbackQuery);
        } catch (Exception ex) {
            Long chatId = CallbackUtility.getChatId(callbackQuery);
            // Handle exceptions in a context-aware manner
            ExceptionProcessorUtility.handleUpdateProcessorException(ex, chatId);
        }
    }

    /**
     * Executes the callback action for the given step index.
     *
     * @param actions the list of callback action functions
     * @param stepIndex the index of the action to execute
     * @param callbackQuery the callback query object
     */
    private void executeAction(List<CallbackActionFunction> actions, int stepIndex, CallbackQuery callbackQuery) {
        // Build the parameter DTO and apply the action function
        actions.get(stepIndex).apply(ObjectsFactory.buildIncomingCallbackContext(callbackQuery));
    }

    /**
     * Validates the list of actions for the given key and step index.
     * Throws an exception if the actions list is missing or the index is out of bounds.
     *
     * @param dataDetails the callback data details containing key and step index
     * @return the validated list of actions
     * @throws NoSuchElementException if actions are missing or index is out of bounds
     */
    private List<CallbackActionFunction> getValidatedActions(CallbackDataDetails dataDetails) {
        String key = dataDetails.getKey();
        int index = dataDetails.getStepIndex();

        List<CallbackActionFunction> actions = callbackActionMap.get(key);

        if (actions == null) {
            throw new NoSuchElementException("No callback action found for key: " + key);
        }
        if (index >= actions.size()) {
            throw new NoSuchElementException(String.format("No step found for key %s and index %s: ", key, index));
        }

        return actions;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a predicate that matches updates containing a callbackQuery.
     *
     * @return {@inheritDoc}
     */
    @Override
    public Predicate<Update> getProcessorFinder() {
        return (update) -> update.getCallbackQuery() != null;
    }

    /**
     * Extracts {@link CallbackDataDetails} from the callback data string.
     * Handles different data formats based on known prefixes.
     *
     * @param data the callback data string
     * @return the extracted {@link CallbackDataDetails}
     * @throws JsonProcessingException if JSON parsing fails
     */
    private CallbackDataDetails extractDataDetails(String data) throws JsonProcessingException {
        // Handle simple page or button reference
        if (data.startsWith(CallbackConstants.PAGE_REFERENCE_PREFIX)
            || data.startsWith(CallbackConstants.BUTTON_REFERENCE_PREFIX)) {
            return CallbackUtility.buildDataDetails(data, 0, data);
        }

        // Handle JSON-encoded data details
        if (data.startsWith(CallbackConstants.DATA_REFERENCE_PREFIX)) {
            String dataDetailsJson = data.substring(CallbackConstants.DATA_REFERENCE_PREFIX.length());
            return objectMapper.readValue(dataDetailsJson, CallbackDataDetails.class);
        }

        // Throw if callback data format is not managed
        throw new NoSuchElementException("Callback not managed: " + data);
    }
}
