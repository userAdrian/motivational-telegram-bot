package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.infrastructure.cache.provider.RedisCacheConfigurationsProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Map;

/**
 * Configuration class for setting up the application's cache manager.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures and provides the application's {@link CacheManager} bean using Redis.
     *
     * @param redisConnectionFactory the Redis connection factory
     * @return the configured {@link CacheManager}
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Retrieve cache configurations from the provider
        Map<String, RedisCacheConfiguration> cacheConfigs = RedisCacheConfigurationsProvider.getCacheMapConfiguration();

        // Build and return the RedisCacheManager with the initial configurations
        return RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }

}
