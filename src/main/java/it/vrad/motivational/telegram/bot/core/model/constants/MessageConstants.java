package it.vrad.motivational.telegram.bot.core.model.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

/**
 * Constants for message templates and default values
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {
    public static final String PHRASE_TEMPLATE = "%s\n\n~%s";
    public static final String PHRASE_BIOGRAPHY_TEMPLATE = PHRASE_TEMPLATE + " Biography";

    public static final Locale DEFAULT_LOCALE = Locale.ITALIAN;
}
