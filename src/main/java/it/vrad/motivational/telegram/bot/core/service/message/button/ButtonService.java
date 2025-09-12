package it.vrad.motivational.telegram.bot.core.service.message.button;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;

import java.util.Locale;

/**
 * Service interface for building inline keyboard buttons for Telegram messages.
 */
public interface ButtonService {

    /**
     * Builds an {@link InlineKeyboardButton} with localized text and callback data.
     *
     * @param locale the locale for the button text
     * @param propertyName the property name used for localization
     * @param callback the callback data for the button
     * @return the constructed InlineKeyboardButton
     */
    InlineKeyboardButton buildInlineKeyboardButton(Locale locale, String propertyName, String callback);

}
