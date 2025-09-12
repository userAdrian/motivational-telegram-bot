package it.vrad.motivational.telegram.bot.infrastructure.config.validator;

import it.vrad.motivational.telegram.bot.config.properties.PageConfigurationProperties;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.AdminPage;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InfoPage;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InitialPage;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.StatisticsPage;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.ButtonCoordinates;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Validates page button configuration properties.
 */
@Slf4j
public class PageConfigurationPropertiesValidator {

    // Error message constants for validation failures
    private static final String ERR_CONFIG_MISSING_PAGE = "Configuration not present for page: ";
    private static final String ERR_AUTH_CONFIG_MISSING_PAGE = "AUTH Configuration not present for page: ";
    private static final String ERR_CONFIG_MISSING_BUTTON = "Configuration not present for page %s and button %s";
    private static final String ERR_CONFIG_MISSING_BUTTON_AUTH_ROLES = "AUTH Configuration not present for page %s and button %s";
    private static final String ERR_CONFIG_EMPTY_BUTTON_COORDINATES = "Empty coordinates for page %s and button %s: ";
    private static final String ERR_CONFIG_EMPTY_BUTTON_AUTH_ROLES = "Empty AUTH roles for page %s and button %s";
    private static final String ERR_TOO_MANY_BUTTONS = "More than %s buttons per row for page %s";
    private static final String ERR_MAX_BUTTONS_NON_POSITIVE = "maxButtonsPerRow property must be positive";

    /**
     * Entry point for validating the given page configuration properties.
     */
    public void validate(PageConfigurationProperties properties) {
        // Validate that maxButtonsPerRow is a positive integer
        validateMaxButtonsPerRow(properties.getMaxButtonsPerRow());

        // Validate button configuration and allowed roles for all page types
        validatePageButtonsConfigurationMap(
                properties.getPageButtonsConfigurationMap(),
                properties.getMaxButtonsPerRow(),
                properties.getPageAllowedRolesMap()
        );
    }

    /**
     * Validates that maxButtonsPerRow is positive.
     */
    private void validateMaxButtonsPerRow(int maxButtonsPerRow) {
        if (maxButtonsPerRow <= 0) {
            fail(ERR_MAX_BUTTONS_NON_POSITIVE);
        }
    }

    /**
     * Validates the page buttons configuration map for all page types.
     * Iterates over all page enums and validates their button and role configuration.
     */
    private void validatePageButtonsConfigurationMap(
            Map<String, Map<String, ButtonCoordinates>> pageButtonsConfigurationMap,
            int maxButtonsPerRow,
            Map<String, Map<String, Set<UserRole>>> pageAllowedRolesMap) {

        // Validate InitialPage buttons
        validatePageButtons(InitialPage.values(), pageButtonsConfigurationMap, maxButtonsPerRow, pageAllowedRolesMap);
        // Validate InfoPage buttons
        validatePageButtons(InfoPage.values(), pageButtonsConfigurationMap, maxButtonsPerRow, pageAllowedRolesMap);
        // Validate StatisticsPage buttons
        validatePageButtons(StatisticsPage.values(), pageButtonsConfigurationMap, maxButtonsPerRow, pageAllowedRolesMap);
        // Validate AdminPage buttons
        validatePageButtons(AdminPage.values(), pageButtonsConfigurationMap, maxButtonsPerRow, pageAllowedRolesMap);
    }

    /**
     * Validates configuration for a page.
     * Checks presence of page/button config and allowed roles, and ensures button count per row does not exceed max.
     */
    private <T extends PageEnum> void validatePageButtons(
            T[] pageButtons,
            Map<String, Map<String, ButtonCoordinates>> pageButtonsConfigurationMap,
            int maxButtonsPerRow,
            Map<String, Map<String, Set<UserRole>>> pageAllowedRolesMap) {

        String pageName = pageButtons.getClass().getComponentType().getSimpleName();
        Map<String, ButtonCoordinates> buttonsMap = pageButtonsConfigurationMap.get(pageName);
        Map<String, Set<UserRole>> allowedRolesMap = pageAllowedRolesMap.get(pageName);

        // Ensure page and roles configuration exist
        validatePagePresence(pageName, buttonsMap, allowedRolesMap);

        int maxColumnIndex = 0;
        for (T button : pageButtons) {
            String buttonName = button.name();
            // Ensure button and its roles configuration exist
            validateButtonPresence(pageName, buttonName, buttonsMap, allowedRolesMap);
            // Extract column index from button coordinate
            int columnIndex = buttonsMap.get(buttonName).column();
            maxColumnIndex = Math.max(maxColumnIndex, columnIndex);
        }

        // Check that the number of buttons per row does not exceed the allowed maximum
        if (maxColumnIndex > maxButtonsPerRow - 1) {
            fail(String.format(ERR_TOO_MANY_BUTTONS, maxButtonsPerRow, pageName));
        }
    }

    /**
     * Validates the presence of page configuration and authorization roles configuration.
     */
    private void validatePagePresence(String pageName, Map<String, ButtonCoordinates> buttonsMap,
                                      Map<String, Set<UserRole>> allowedRolesMap) {
        if (Objects.isNull(buttonsMap)) {
            fail(ERR_CONFIG_MISSING_PAGE + pageName);
        }
        if (Objects.isNull(allowedRolesMap)) {
            fail(ERR_AUTH_CONFIG_MISSING_PAGE + pageName);
        }
    }

    /**
     * Validates the presence of button configuration and its associated authorization roles.
     * Also checks that the roles set is not empty.
     */
    private void validateButtonPresence(String pageName, String buttonName, Map<String, ButtonCoordinates> buttonsMap,
                                        Map<String, Set<UserRole>> allowedRolesMap) {
        if (!buttonsMap.containsKey(buttonName)) {
            fail(String.format(ERR_CONFIG_MISSING_BUTTON, pageName, buttonName));
        } else {
            ButtonCoordinates buttonCoordinates = buttonsMap.get(buttonName);
            if(Objects.isNull(buttonCoordinates)){
                fail(String.format(ERR_CONFIG_EMPTY_BUTTON_COORDINATES, pageName, buttonName));
            }
        }

        if (!allowedRolesMap.containsKey(buttonName)) {
            fail(String.format(ERR_CONFIG_MISSING_BUTTON_AUTH_ROLES, pageName, buttonName));
        } else {
            Set<UserRole> userRoles = allowedRolesMap.get(buttonName);
            if (CollectionUtils.isEmpty(userRoles)) {
                fail(String.format(ERR_CONFIG_EMPTY_BUTTON_AUTH_ROLES, pageName, buttonName));
            }
        }
    }

    /**
     * Logs and throws an IllegalStateException with the given message.
     */
    private void fail(String errorMessage) {
        log.error(errorMessage);
        throw new IllegalStateException(errorMessage);
    }

}