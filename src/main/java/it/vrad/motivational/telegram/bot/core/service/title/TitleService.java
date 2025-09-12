package it.vrad.motivational.telegram.bot.core.service.title;

import java.util.Locale;

/**
 * Service interface for retrieving localized titles based on property names.
 */
public interface TitleService {

    /**
     * Returns the localized button title for the given property name and locale.
     *
     * @param propertyName the property name to look up
     * @param locale the locale for localization
     * @return the localized title string
     */
    String getButtonTitle(String propertyName, Locale locale);

}
