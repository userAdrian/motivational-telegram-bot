package it.vrad.motivational.telegram.bot.core.processor.update;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import it.vrad.motivational.telegram.bot.core.processor.Processor;

import java.util.function.Consumer;

/**
 * Processor interface for handling {@link Update} objects.
 * Extends {@link Consumer} for processing and {@link Processor} for selection logic.
 */
public interface UpdateProcessor extends Consumer<Update>, Processor<Update> {

}
