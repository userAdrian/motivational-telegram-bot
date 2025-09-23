package it.vrad.motivational.telegram.bot.core.service.auth;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;

import java.util.Set;

/**
 * Service interface for manage user roles.
 */
public interface AuthRoleService {

    /**
     * Retrieves the set of authorized user roles for the specified command.
     *
     * @param command the command for which to retrieve authorized roles (must not be blank)
     * @return a set of {@link UserRole} authorized for the command
     * @throws IllegalArgumentException if the command is blank or null
     */
    Set<UserRole> getAuthRolesForCommand(String command);

    /**
     * Retrieves the set of authorized user roles for the specified page.
     *
     * @param page the page for which to retrieve authorized roles (must not be blank)
     * @return a set of {@link UserRole} authorized for the page
     * @throws IllegalArgumentException if the page is blank or null
     */
    Set<UserRole> getAuthRolesForPage(String page);
}
