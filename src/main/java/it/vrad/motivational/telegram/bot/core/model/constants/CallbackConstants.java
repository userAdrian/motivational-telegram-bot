package it.vrad.motivational.telegram.bot.core.model.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for Telegram callbacks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallbackConstants {
    public static final String PAGE_REFERENCE_PREFIX = "page_";
    public static final String BUTTON_REFERENCE_PREFIX = "button_";
    public static final String DATA_REFERENCE_PREFIX = "data_";

}
