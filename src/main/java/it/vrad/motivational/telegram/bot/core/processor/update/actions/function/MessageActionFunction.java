package it.vrad.motivational.telegram.bot.core.processor.update.actions.function;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Functional interface for processing an {@link IncomingMessageContext} and producing a {@code Optional<CallbackQueryDto>}.
 * <p>
 * Used to define actions that handle incoming messages and generate responses.
 * <p>
 * Note: The returned {@code Optional<MessageDto>} can be {@code empty} depending on the implementation.
 * Always check the implementing method's documentation for details.
 */
public interface MessageActionFunction extends Function<IncomingMessageContext, Optional<MessageDto>> {

}
