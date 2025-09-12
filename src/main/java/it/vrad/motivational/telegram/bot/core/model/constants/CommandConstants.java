package it.vrad.motivational.telegram.bot.core.model.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains command constants and related properties for the motivational bot.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandConstants {
    public static final String NOT_AVAILABLE = "N/A";
    public static final String BOT_COMMAND_PREFIX = "/";

    public static final String PROCESS_NOT_STARTED = "PROCESS_NOT_STARTED";
    public static final int STEP_0 = 0;
    public static final int STEP_1 = 1;

    //START ---> COMMANDS
    public static class RandomPhrase {
        public static final String TEXT = "/randomphrase";
    }

    public static class Initial {
        public static final String TEXT = "/start";
        public static final String MESSAGE_PROPERTY = "motivational.telegram.bot.messages.initial.template";
    }

    public static class LoadFilePhrases {
        public static final String TEXT = "/loadfilephrases";
        public static final String MESSAGE_PROPERTY = "motivational.telegram.bot.messages.command.load-file-phrases";
        public static final String MESSAGE_PROPERTY_STEP_ONE = "motivational.telegram.bot.messages.command.load-file-phrases.step-one";
    }
    //END ---> COMMANDS

}
