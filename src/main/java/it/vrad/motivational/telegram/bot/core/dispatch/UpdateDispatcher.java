package it.vrad.motivational.telegram.bot.core.dispatch;

import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;

/**
 * Dispatcher type for handling Telegram Update objects.
 */
public interface UpdateDispatcher extends Dispatcher<UpdateProcessorType, Update> {
}
