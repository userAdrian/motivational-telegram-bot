package it.vrad.motivational.telegram.bot.infrastructure.cache.model;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
public class StepDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = -7096048202613994843L;

    private final String command;
    private final int step;

    public boolean isThereAProcessStarted() {
        return !CommandConstants.PROCESS_NOT_STARTED.equals(command) && step != CommandConstants.STEP_0;
    }
}
