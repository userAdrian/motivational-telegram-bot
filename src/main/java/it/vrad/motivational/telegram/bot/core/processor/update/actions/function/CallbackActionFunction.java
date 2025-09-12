package it.vrad.motivational.telegram.bot.core.processor.update.actions.function;

import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;

import java.util.function.Function;

/**
 * Functional interface for processing an {@link IncomingCallbackContext} and producing a {@link CallbackQueryDto}.
 * <p>
 * Used to define actions that handle incoming callback queries and generate responses.
 * <p>
 * Note: The returned {@link CallbackQueryDto} can be {@code null} depending on the implementation.
 * Always check the implementing method's documentation for details.
 */
public interface CallbackActionFunction extends Function<IncomingCallbackContext, CallbackQueryDto> {
}
