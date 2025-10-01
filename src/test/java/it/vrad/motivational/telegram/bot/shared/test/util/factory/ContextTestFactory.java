package it.vrad.motivational.telegram.bot.shared.test.util.factory;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Factory utility for creating context instances used in unit tests.
 * <p>
 * This class provides static helper methods to build common test contexts.
 * <p>
 * The class is non-instantiable and intended solely for use from tests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextTestFactory {

    // === IncomingMessageContext ===
    public static IncomingMessageContext createGenericIncomingMessageCtx() {
        return IncomingMessageContext.builder()
                .sentMessage(TelegramTestObjectFactory.createGenericMessage())
                .userFromDB(PersistenceTestFactory.createGenericUserDto())
                .build();
    }

    public static IncomingMessageContext createGenericIncomingMessageCtxNoUser() {
        return IncomingMessageContext.builder()
                .sentMessage(TelegramTestObjectFactory.createGenericMessage())
                .build();
    }

}
