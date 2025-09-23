package it.vrad.motivational.telegram.bot.core.service.auth.impl;

import it.vrad.motivational.telegram.bot.config.properties.CommandProperties;
import it.vrad.motivational.telegram.bot.config.properties.PageConfigurationProperties;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.service.auth.AuthRoleService;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of {@link AuthRoleService}
 */
@RequiredArgsConstructor
@Service
public class AuthRoleServiceImpl implements AuthRoleService {
    private final CommandProperties commandProperties;
    private final PageConfigurationProperties pageConfigurationProperties;


    /**
     * {@inheritDoc}
     *
     * @param command {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public Set<UserRole> getAuthRolesForCommand(String command) {
        ExceptionUtility.requireNonBlank(command);

        return commandProperties.getAuthorizedRolesForCommand(command);
    }

    /**
     * {@inheritDoc}
     *
     * @param page {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public Set<UserRole> getAuthRolesForPage(String page) {
        ExceptionUtility.requireNonBlank(page);

        return pageConfigurationProperties.getAuthorizedRolesForPage(page);
    }
}
