package it.vrad.motivational.telegram.bot.core.service.phrase.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AuthorDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.PhraseDao;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.shared.util.CommonUtility;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Implementation of {@link PhraseService}
 * for phrase operations.
 */
@Slf4j
@Service
public class PhraseServiceImpl implements PhraseService {
    private final PhraseDao phraseDao;
    private final AuthorDao authorDao;
    private final UserPhraseService userPhraseService;

    public PhraseServiceImpl(PhraseDao phraseDao, AuthorDao authorDao, UserPhraseService userPhraseService) {
        this.phraseDao = phraseDao;
        this.authorDao = authorDao;
        this.userPhraseService = userPhraseService;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<PhraseDto> getRandomPhrase(Long userId) {
        // Retrieve all available phrases for the user
        List<PhraseDto> phrasesToSend = phraseDao.findAllAvailablePhrases(userId);

        if (phrasesToSend.isEmpty()) {
            // If none available, reset phrases and try again
            phrasesToSend = userPhraseService.resetPhrases(userId);

            if (phrasesToSend.isEmpty()) {
                // No phrases to process after reset
                return Optional.empty();
            }
        }

        // Select and return a random phrase
        return Optional.of(CommonUtility.selectRandomElement(phrasesToSend));
    }

    /**
     * {@inheritDoc}
     *
     * @param phraseDtos {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    @Override
    public List<PhraseDto> save(Set<PhraseDto> phraseDtos) {
        // Group new phrases by author, filtering out phrases that already exist (by text hash)
        Map<AuthorDto, List<PhraseDto>> newPhrasesMap = groupNewPhrasesByAuthor(phraseDtos);

        // For each group, ensure the author exists and assign the author ID to each phrase
        Set<PhraseDto> newPhrases = assignAuthorIds(newPhrasesMap);

        // If there are no new phrases to save, log a warning and return an empty list
        if (newPhrases.isEmpty()) {
            log.warn("No phrases to save");
            return List.of();
        }

        // Persist all new phrases and return the result
        return phraseDao.saveAll(newPhrases);
    }

    /**
     * Retrieves the set of hash codes for the text of all existing phrases.
     * Used for duplicate detection when saving new phrases.
     *
     * @return a set of hash codes representing existing phrase texts
     */
    private Set<Integer> getExistingPhraseHashes() {
        return phraseDao.findAll().stream()
                .map(p -> p.getText().hashCode())
                .collect(Collectors.toSet());
    }

    /**
     * Groups new phrases by their associated author, filtering out phrases that already exist
     * (based on text hash). This method first retrieves all existing phrase text hashes to avoid
     * saving duplicates, then groups the new, unique phrases by their AuthorDto.
     *
     * @param phraseDtos the set of phrase DTOs to process
     * @return a map where the key is the AuthorDto and the value is a list of new PhraseDto for that author
     */
    private Map<AuthorDto, List<PhraseDto>> groupNewPhrasesByAuthor(Set<PhraseDto> phraseDtos) {
        // Retrieve all existing phrases and compute their text hashes for duplicate detection
        Set<Integer> existingHashes = getExistingPhraseHashes();

        return phraseDtos.stream()
                .filter(dto -> dto.getText() != null && !existingHashes.contains(dto.getText().hashCode()))
                .collect(Collectors.groupingBy(PhraseDto::getAuthor));
    }

    /**
     * For each group of phrases by author, ensures the author exists (creates if necessary)
     * and assigns the author ID to each phrase.
     *
     * @param newPhrasesMap a map of AuthorDto to lists of PhraseDto to be saved
     * @return a set of PhraseDto with author IDs assigned
     */
    private Set<PhraseDto> assignAuthorIds(Map<AuthorDto, List<PhraseDto>> newPhrasesMap) {
        return newPhrasesMap.entrySet().stream()
                .flatMap(entry -> {
                    Long authorId = authorDao.getOrCreateAuthorId(entry.getKey());

                    return entry.getValue().stream()
                            .peek(phraseDto -> phraseDto.getAuthor().setId(authorId));
                }).collect(Collectors.toSet());
    }

}
