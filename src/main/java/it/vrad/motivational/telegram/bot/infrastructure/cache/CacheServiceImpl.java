package it.vrad.motivational.telegram.bot.infrastructure.cache;

import it.vrad.motivational.telegram.bot.infrastructure.cache.model.StepDetail;
import it.vrad.motivational.telegram.bot.infrastructure.cache.factory.StepDetailFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link CacheService} using Spring Cache abstraction.
 */
@Service
public class CacheServiceImpl implements CacheService {

    /**
     * {@inheritDoc}
     * <p>
     * The default StepDetail is created using {@link StepDetailFactory#createDefault()}.
     *
     * @param chatId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Cacheable(value = CacheConstants.COMMAND_STEP_CACHE_NAME, key = CacheConstants.COMMAND_STEP_CACHE_KEY)
    public StepDetail getStepDetail(Long chatId) {
        // If not present in cache, return a default StepDetail
        return StepDetailFactory.createDefault();
    }

    /**
     * {@inheritDoc}
     *
     * @param chatId     {@inheritDoc}
     * @param stepDetail {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @CachePut(value = CacheConstants.COMMAND_STEP_CACHE_NAME, key = CacheConstants.COMMAND_STEP_CACHE_KEY)
    public StepDetail saveStepDetail(Long chatId, StepDetail stepDetail) {
        Objects.requireNonNull(stepDetail);
        return stepDetail;
    }

    /**
     * {@inheritDoc}
     *
     * @param chatId {@inheritDoc}
     */
    @Override
    @CacheEvict(value = CacheConstants.COMMAND_STEP_CACHE_NAME, key = CacheConstants.COMMAND_STEP_CACHE_KEY)
    public void removeStepDetail(Long chatId) {
        // No implementation needed; annotation handles eviction
    }

}
