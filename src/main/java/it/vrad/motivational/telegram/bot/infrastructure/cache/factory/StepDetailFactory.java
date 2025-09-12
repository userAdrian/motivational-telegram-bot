package it.vrad.motivational.telegram.bot.infrastructure.cache.factory;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.infrastructure.cache.model.StepDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Factory class for creating StepDetail objects for command processing steps.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StepDetailFactory {

    /**
     * Creates a default StepDetail.
     * @return default StepDetail instance
     */
    public static StepDetail createDefault(){
        return StepDetail.builder()
                .command(CommandConstants.PROCESS_NOT_STARTED)
                .step(CommandConstants.STEP_0)
                .build();
    }

    /**
     * Creates a StepDetail for a given command and step.
     * @param command the command string
     * @param step the step number
     * @return StepDetail instance
     */
    public static StepDetail of(String command, int step){
        return StepDetail.builder()
                .command(command)
                .step(step)
                .build();
    }

    /**
     * Creates a StepDetail for the first step of "loading phrases from file" command.
     * @return StepDetail instance for load phrases step one
     */
    public static StepDetail loadFilePhrasesStepOne(){
        return of(CommandConstants.LoadFilePhrases.TEXT, CommandConstants.STEP_1);
    }
}
