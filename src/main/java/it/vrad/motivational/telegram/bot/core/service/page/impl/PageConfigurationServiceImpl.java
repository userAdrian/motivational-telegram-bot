package it.vrad.motivational.telegram.bot.core.service.page.impl;

import it.vrad.motivational.telegram.bot.config.properties.PageConfigurationProperties;
import it.vrad.motivational.telegram.bot.core.model.ButtonCoordinates;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageButton;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.service.message.button.ButtonService;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.shared.util.CommonUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of {@link PageConfigurationService}
 * for configuring page buttons.
 */
@Service
public class PageConfigurationServiceImpl implements PageConfigurationService {
    private final ButtonService buttonService;

    private final Map<String, Map<String, ButtonCoordinates>> pageButtonsConfigurationMap;
    private final int maxButtonsPerRow;
    private final Map<String, Map<String, Set<UserRole>>> pageButtonsAllowedRolesMap;

    public PageConfigurationServiceImpl(
            PageConfigurationProperties pageConfigurationProperties,
            ButtonService buttonService
    ) {
        this.buttonService = buttonService;

        this.pageButtonsConfigurationMap = pageConfigurationProperties.getPageButtonsConfigurationMap();
        this.maxButtonsPerRow = pageConfigurationProperties.getMaxButtonsPerRow();
        this.pageButtonsAllowedRolesMap = pageConfigurationProperties.getPageButtonsAllowedRolesMap();
    }

    /**
     * {@inheritDoc}
     *
     * @param locale  {@inheritDoc}
     * @param buttons {@inheritDoc}
     * @param user    {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public <T extends PageButton> InlineKeyboardButton[][] getPageButtons(Locale locale, T[] buttons, UserDto user) {
        InlineKeyboardButton[][] inlineKeyboardButtonMatrix = initializeInlineKeyboardButtonMatrix(buttons.length);

        for (T button : buttons) {
            ButtonCoordinates buttonCoordinates = getButtonCoordinates(button);
            Set<UserRole> userRoles = getButtonUserRoles(button);

            // Only add button if user matches allowed roles
            if (user.matchesAnyRole(userRoles)) {
                inlineKeyboardButtonMatrix[buttonCoordinates.row()][buttonCoordinates.column()] =
                        buttonService.buildInlineKeyboardButton(locale, button.getTitlePropertyName(), button.getReference());
            }
        }

        // Remove null values from the button matrix before returning
        return CommonUtility.removeNullValues(inlineKeyboardButtonMatrix);
    }

    private Set<UserRole> getButtonUserRoles(PageButton button) {
        return pageButtonsAllowedRolesMap.get(button.getPageReference()).get(button.name());
    }

    private InlineKeyboardButton[][] initializeInlineKeyboardButtonMatrix(int totalButtons) {
        return new InlineKeyboardButton[totalButtons][maxButtonsPerRow];
    }

    private ButtonCoordinates getButtonCoordinates(PageButton button) {
        return pageButtonsConfigurationMap.get(button.getPageReference()).get(button.name());
    }

}
