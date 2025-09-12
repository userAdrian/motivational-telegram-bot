package it.vrad.motivational.telegram.bot.core.service.phrase.user.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.infrastructure.cache.CacheConstants;
import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserPhraseDao;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import it.vrad.motivational.telegram.bot.core.util.StatisticsPageUtility;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link UserPhraseService}
 * for user phrase operations.
 */
@Service
public class UserPhraseServiceImpl implements UserPhraseService {
    private final UserPhraseDao userPhraseDao;

    /**
     * Constructs a UserPhraseServiceImpl with the provided UserPhraseDao.
     *
     * @param userPhraseDao the DAO for user phrase persistence
     */
    public UserPhraseServiceImpl(UserPhraseDao userPhraseDao) {
        this.userPhraseDao = userPhraseDao;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<PhraseDto> resetPhrases(Long userId) {
        return userPhraseDao.resetReadFlag(userId).stream()
                .map(UserPhraseDto::getPhraseDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Annotated with {@link org.springframework.cache.annotation.CacheEvict} to evict the statistics page cache
     * for the user after method completion
     *
     * @param userId   {@inheritDoc}
     * @param phraseId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @CacheEvict(value = CacheConstants.STATISTICS_PAGE_CACHE_NAME, key = CacheConstants.STATISTICS_PAGE_CACHE_KEY)
    public UserPhraseDto updateUserPhraseReadStatus(Long userId, Long phraseId) {
        // Try to find the user-phrase relation
        Optional<UserPhraseDto> userPhraseOpt = userPhraseDao.findUserPhraseById(userId, phraseId);

        if (userPhraseOpt.isPresent()) {
            // If found, mark as read and increment counter
            return userPhraseDao.markAsReadAndIncrement(userPhraseOpt.get());
        }

        // If not found, create a new user-phrase relation
        return userPhraseDao.saveUserPhrase(PersistenceDtoFactory.buildInitialUserPhraseDto(userId, phraseId));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Annotated with {@link org.springframework.cache.annotation.Cacheable} to cache the statistics page details
     * for the user.
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Cacheable(value = CacheConstants.STATISTICS_PAGE_CACHE_NAME, key = CacheConstants.STATISTICS_PAGE_CACHE_KEY)
    public StatisticsPageDetails fetchStatisticsPageDetails(Long userId) {
        Objects.requireNonNull(userId);

        // Retrieve all valid user-phrase relations for the user
        List<UserPhraseDto> userPhraseList = userPhraseDao.findAllValidUserPhrase(userId);

        // Group user phrases by author
        Map<AuthorDto, List<UserPhraseDto>> authorToUserPhrasesMap = StatisticsPageUtility.groupUserPhrasesByAuthor(userPhraseList);

        // Get phrases from the least and most viewed authors
        List<PhraseDto> leastViewedAuthorPhrases = StatisticsPageUtility.getPhrasesFromLeastViewedAuthor(authorToUserPhrasesMap);
        List<PhraseDto> mostViewedAuthorPhrases = StatisticsPageUtility.getPhrasesFromMostViewedAuthor(authorToUserPhrasesMap);

        // Build and return the statistics details object
        return StatisticsPageDetails.builder()
                .totalPhrases(StatisticsPageUtility.totalPhrases(userPhraseList))
                .lessViewedPhrase(StatisticsPageUtility.getFirstPhrase(leastViewedAuthorPhrases))
                .mostViewedPhrase(StatisticsPageUtility.getFirstPhrase(mostViewedAuthorPhrases))
                .build();
    }
}
