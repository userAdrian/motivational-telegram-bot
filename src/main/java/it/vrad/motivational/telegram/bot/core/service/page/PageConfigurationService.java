package it.vrad.motivational.telegram.bot.core.service.page;

import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;

import java.util.Locale;

/**
 * Service interface for configuring page buttons.
 */
public interface PageConfigurationService {
    /**
     * Returns a matrix of {@link InlineKeyboardButton} for the specified page.
     *
     * @param locale the locale for button labels
     * @param values the array of page enum values representing buttons
     * @param user the user for role-based button filtering
     * @param <T> the type of PageEnum
     * @return a matrix of InlineKeyboardButton objects for the page
     */
    <T extends PageEnum> InlineKeyboardButton[][] getPageButtons(Locale locale, T[] values, UserDto user);
}
