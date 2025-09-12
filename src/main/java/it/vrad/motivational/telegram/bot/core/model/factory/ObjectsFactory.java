package it.vrad.motivational.telegram.bot.core.model.factory;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Factory class for creating objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectsFactory {

    /**
     * Builds an IncomingMessageContext from a Message object.
     *
     * @param message the Telegram message
     * @return the constructed IncomingMessageContext
     */
    public static IncomingMessageContext buildMessageParameterDto(Message message){
        return IncomingMessageContext.builder()
                .messageSent(message)
                .build();
    }

    /**
     * Builds an IncomingCallbackContext from a CallbackQuery object.
     *
     * @param callbackQuery the Telegram callback query
     * @return the constructed IncomingCallbackContext
     */
    public static IncomingCallbackContext buildCallbackParameterDto(CallbackQuery callbackQuery){
        return IncomingCallbackContext.builder()
                .callbackQuery(callbackQuery)
                .build();
    }
}
