package it.vrad.motivational.telegram.bot.config.properties;

import it.vrad.motivational.telegram.bot.infrastructure.config.validator.PageConfigurationPropertiesValidator;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.ButtonCoordinates;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

/**
 * Configuration properties for page button layouts and allowed roles.
 * Used to configure button positions and access control for different pages.
 */
@Validated
@Data
@ConfigurationProperties(prefix = "motivational.telegram.bot.configuration.page")
@Component
public class PageConfigurationProperties {
    @NotNull
    private int maxButtonsPerRow;

    @NotNull
    private Map<String, Map<String, ButtonCoordinates>> pageButtonsConfigurationMap;

    @NotNull
    private Map<String, Set<UserRole>> pageAllowedRolesMap;

    @NotNull
    private Map<String, Map<String, Set<UserRole>>> pageButtonsAllowedRolesMap;

    /**
     * Validates the page configuration properties after construction.
     */
    @PostConstruct
    private void postConstruct() {
        PageConfigurationPropertiesValidator validator = new PageConfigurationPropertiesValidator();
        validator.validate(this);
    }

    public Set<UserRole> getAuthorizedRolesForPage(String page){
        Set<UserRole> userRoles = pageAllowedRolesMap.get(page);

        if(CollectionUtils.isEmpty(userRoles)){
            throw new IllegalArgumentException("No auth roles found for page: " + page);
        }
        return userRoles;
    }
}
