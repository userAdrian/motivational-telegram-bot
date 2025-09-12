package it.vrad.motivational.telegram.bot.infrastructure.cache.provider;

import it.vrad.motivational.telegram.bot.infrastructure.cache.CacheConstants;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.Map;

/**
 * Provider for Redis cache configurations.
 * Supplies cache names and their corresponding Redis cache configuration (e.g. TTL).
 */
public class RedisCacheConfigurationsProvider {

    /**
     * Returns a map of cache names to their Redis cache configurations.
     *
     * @return a map of cache names to {@link RedisCacheConfiguration}
     */
    public static Map<String, RedisCacheConfiguration> getCacheMapConfiguration() {
        Map<String, RedisCacheConfiguration> cacheMap = new java.util.HashMap<>();

        cacheMap.put(CacheConstants.COMMAND_STEP_CACHE_NAME, getCommandStepCacheRedisConfiguration());
        cacheMap.put(CacheConstants.STATISTICS_PAGE_CACHE_NAME, getStatisticsPageCacheRedisConfiguration());

        return cacheMap;
    }

    /**
     * Returns the Redis cache configuration for the command step cache.
     */
    private static RedisCacheConfiguration getCommandStepCacheRedisConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30));
    }

    /**
     * Returns the Redis cache configuration for the statistics page cache.
     */
    private static RedisCacheConfiguration getStatisticsPageCacheRedisConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(6));
    }
}
