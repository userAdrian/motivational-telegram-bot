package it.vrad.motivational.telegram.bot.config.properties;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

/**
 * Configuration properties for commands.
 */
@Validated
@Data
@ConfigurationProperties(prefix = "motivational.telegram.bot.configuration.command")
@Component
public class CommandProperties {

    @NotNull
    private Map<String, Set<UserRole>> authRolesMap;

    @NotNull
    private Set<UserRole> skipCooldownRoles;

    /**
     * Retrieves the set of authorized roles for a given command.
     *
     * @param command the command name
     * @return the set of authorized roles
     * @throws IllegalStateException if no roles are found for the command
     */
    public Set<UserRole> getAuthorizedRolesForCommand(String command) {
        Set<UserRole> roles = authRolesMap.get(command);

        if (CollectionUtils.isEmpty(roles)) {
            throw new IllegalStateException("No roles found for command: " + command);
        }

        return roles;
    }

}
