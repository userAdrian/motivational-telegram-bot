package it.vrad.motivational.telegram.bot.core.service.page.impl;

import it.vrad.motivational.telegram.bot.config.properties.PageConfigurationProperties;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.ButtonCoordinates;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;
import it.vrad.motivational.telegram.bot.core.service.message.button.ButtonService;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.infrastructure.util.CommonUtility;
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
    private final Map<String, Map<String, Set<UserRole>>> pageEnabledRolesMap;

    public PageConfigurationServiceImpl(PageConfigurationProperties pageConfigurationProperties, ButtonService buttonService) {
        this.buttonService = buttonService;

        this.maxButtonsPerRow = pageConfigurationProperties.getMaxButtonsPerRow();
        this.pageButtonsConfigurationMap = pageConfigurationProperties.getPageButtonsConfigurationMap();
        this.pageEnabledRolesMap = pageConfigurationProperties.getPageAllowedRolesMap();
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
    public <T extends PageEnum> InlineKeyboardButton[][] getPageButtons(Locale locale, T[] buttons, UserDto user) {
        InlineKeyboardButton[][] inlineKeyboardButtonMatrix = initializeInlineKeyboardButtonMatrix(buttons.length);
        String pageName = buttons.getClass().getComponentType().getSimpleName();


        for (T button : buttons) {
            ButtonCoordinates buttonCoordinates = getButtonCoordinates(pageName, button.name());
            Set<UserRole> userRoles = getButtonUserRoles(pageName, button.name());

            // Only add button if user matches allowed roles
            if (user.matchesAnyRole(userRoles)) {
                inlineKeyboardButtonMatrix[buttonCoordinates.row()][buttonCoordinates.column()] =
                        buttonService.buildInlineKeyboardButton(locale, button.getTitlePropertyName(), button.getReference());
            }
        }

        // Remove null values from the button matrix before returning
        return CommonUtility.removeNullValues(inlineKeyboardButtonMatrix);
    }

    private Set<UserRole> getButtonUserRoles(String page, String button) {
        return pageEnabledRolesMap.get(page).get(button);
    }

    private InlineKeyboardButton[][] initializeInlineKeyboardButtonMatrix(int totalButtons) {
        return new InlineKeyboardButton[totalButtons][maxButtonsPerRow];
    }

    private ButtonCoordinates getButtonCoordinates(String pageName, String buttonName) {
        return pageButtonsConfigurationMap.get(pageName).get(buttonName);
    }

}
