package it.vrad.motivational.telegram.bot.core.processor.update.actions;

import it.vrad.motivational.telegram.bot.core.facade.message.admin.AdminCommandFacadeService;
import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.MessageActionFunction;
import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.facade.message.InitialPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.PhraseMessageFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provider for building the message action map.
 * Associates command texts with their corresponding message action functions.
 */
@RequiredArgsConstructor
@Component
public class MessageActionMapProvider {

    private final InitialPageMessageFacadeService initialPageMessageFacadeService;
    private final PhraseMessageFacadeService phraseMessageFacadeService;
    private final AdminCommandFacadeService adminCommandFacadeService;

    /**
     * Builds the message action map, associating command texts with lists of message action functions.
     *
     * @return a map from command text to list of {@link MessageActionFunction}
     */
    public Map<String, List<MessageActionFunction>> buildMessageActionMap() {
        Map<String, List<MessageActionFunction>> map = new HashMap<>();

        // Add actions for each command type
        map.putAll(getWelcomeMessagesActionMap());
        map.putAll(getRandomPhraseMessageActionMap());
        map.putAll(getLoadPhrasesMessageActionMap());

        return map;
    }

    /**
     * Builds the action map for welcome/initial messages.
     */
    private Map<String, List<MessageActionFunction>> getWelcomeMessagesActionMap() {
        List<MessageActionFunction> stepList = List.of(initialPageMessageFacadeService::processInitialMessage);

        return Map.of(CommandConstants.Initial.TEXT, stepList);
    }

    /**
     * Builds the action map for the random phrase command.
     */
    private Map<String, List<MessageActionFunction>> getRandomPhraseMessageActionMap() {
        List<MessageActionFunction> stepList = List.of(phraseMessageFacadeService::processRandomPhraseCommand);

        return Map.of(CommandConstants.RandomPhrase.TEXT, stepList);
    }

    /**
     * Builds the action map for the load file phrases command.
     */
    private Map<String, List<MessageActionFunction>> getLoadPhrasesMessageActionMap() {
        List<MessageActionFunction> stepList = List.of(
                adminCommandFacadeService::processLoadFilePhrasesCommand,
                adminCommandFacadeService::processLoadFilePhrasesCommandStepOne
        );

        return Map.of(CommandConstants.LoadFilePhrases.TEXT, stepList);
    }

}
