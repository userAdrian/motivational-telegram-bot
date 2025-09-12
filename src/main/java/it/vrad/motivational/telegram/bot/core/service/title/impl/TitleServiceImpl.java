package it.vrad.motivational.telegram.bot.core.service.title.impl;

import it.vrad.motivational.telegram.bot.core.service.title.TitleService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link TitleService} that retrieves localized titles using Spring's MessageSource.
 */
@Service
public class TitleServiceImpl implements TitleService {

    private final MessageSource messageSource;

    /**
     * Constructs a TitleServiceImpl with the provided MessageSource.
     *
     * @param messageSource the MessageSource for localization
     */
    public TitleServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     * Validates input and retrieves the localized title from MessageSource.
     *
     * @param propertyName {@inheritDoc}
     * @param locale       {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getButtonTitle(String propertyName, Locale locale) {
        Objects.requireNonNull(propertyName);
        Objects.requireNonNull(locale);

        // Retrieve the localized message
        return messageSource.getMessage(propertyName, null, locale);
    }
}
