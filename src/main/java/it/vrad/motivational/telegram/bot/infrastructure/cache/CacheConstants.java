package it.vrad.motivational.telegram.bot.infrastructure.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for cache names and keys used in the application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstants {
    public static final String COMMAND_STEP_CACHE_NAME = "commandStepCache";
    public static final String COMMAND_STEP_CACHE_KEY = "#chatId";

    public static final String STATISTICS_PAGE_CACHE_NAME = "statisticsPageCache";
    public static final String STATISTICS_PAGE_CACHE_KEY = "#userId";
}
