package it.vrad.motivational.telegram.bot.infrastructure.cache;

import it.vrad.motivational.telegram.bot.infrastructure.cache.model.StepDetail;

/**
 * Service interface for managing cache operations related to StepDetail objects.
 */
public interface CacheService {

    /**
     * Retrieves the StepDetail object associated with the given chat ID from the cache.
     * If not present, a default StepDetail is created.
     *
     * @param chatId the chat ID for which to retrieve the StepDetail
     * @return the cached StepDetail object, or a default if not present
     */
    StepDetail getStepDetail(Long chatId);

    /**
     * Saves the provided StepDetail object in the cache for the given chat ID.
     *
     * @param chatId the chat ID for which to cache the StepDetail
     * @param stepDetail the StepDetail object to cache
     * @return the cached StepDetail object
     */
    StepDetail saveStepDetail(Long chatId, StepDetail stepDetail);

    /**
     * Removes the StepDetail object associated with the given chat ID from the cache.
     *
     * @param chatId the chat ID for which to remove the StepDetail from cache
     */
    void removeStepDetail(Long chatId);
}
