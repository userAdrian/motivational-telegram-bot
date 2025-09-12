package it.vrad.motivational.telegram.bot.core.util;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class for statistics-related operations on phrases and user phrases.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsPageUtility {


    public static int totalPhrases(List<UserPhraseDto> userPhraseDtos) {
        Objects.requireNonNull(userPhraseDtos);

        return userPhraseDtos.stream()
                .map(UserPhraseDto::getReadCount)
                .reduce(0, Integer::sum);
    }

    /**
     * Groups user phrases by their author.
     *
     * @param userPhrases the list of UserPhraseDto to group
     * @return a map where the key is AuthorDto and the value is a list of UserPhraseDto for that author
     */
    public static Map<AuthorDto, List<UserPhraseDto>> groupUserPhrasesByAuthor(List<UserPhraseDto> userPhrases) {
        Objects.requireNonNull(userPhrases);

        // Group user phrases by the author of their associated phrase
        return userPhrases.stream()
                .collect(Collectors.groupingBy(
                        up -> up.getPhraseDto().getAuthor(),
                        Collectors.toList()
                ));
    }

    /**
     * Returns the list of phrases from the author with the least total views.
     *
     * @param authorUserPhrasesMap map of AuthorDto to list of UserPhraseDto
     * @return list of PhraseDto from the least viewed author, or empty list if none found
     */
    public static List<PhraseDto> getPhrasesFromLeastViewedAuthor(Map<AuthorDto, List<UserPhraseDto>> authorUserPhrasesMap) {
        return getPhrasesFromAuthorByTotalViews(authorUserPhrasesMap, true);
    }

    /**
     * Returns the list of phrases from the author with the most total views.
     *
     * @param authorUserPhrasesMap map of AuthorDto to list of UserPhraseDto
     * @return list of PhraseDto from the most viewed author, or empty list if none found
     */
    public static List<PhraseDto> getPhrasesFromMostViewedAuthor(Map<AuthorDto, List<UserPhraseDto>> authorUserPhrasesMap) {
        return getPhrasesFromAuthorByTotalViews(authorUserPhrasesMap, false);
    }

    /**
     * Returns the list of phrases from the author with either the least or most total views.
     *
     * @param authorUserPhrasesMap map of AuthorDto to list of UserPhraseDto
     * @param least if true, returns phrases from the least viewed author; if false, from the most viewed author
     * @return list of PhraseDto from the selected author, or empty list if none found
     */
    private static List<PhraseDto> getPhrasesFromAuthorByTotalViews(Map<AuthorDto, List<UserPhraseDto>> authorUserPhrasesMap,
                                                                    boolean least) {
        Objects.requireNonNull(authorUserPhrasesMap);

        Comparator<Map.Entry<AuthorDto, List<UserPhraseDto>>> comparator = authorTotalViewsEntryComparator();

        // Select the entry with min or max total views depending on the 'least' flag
        Map.Entry<AuthorDto, List<UserPhraseDto>> selectedEntry = least
                ? authorUserPhrasesMap.entrySet().stream().min(comparator).orElse(null)
                : authorUserPhrasesMap.entrySet().stream().max(comparator).orElse(null);

        if (selectedEntry == null) {
            return Collections.emptyList();
        }

        // Map the selected author's user phrases to PhraseDto list
        return selectedEntry.getValue().stream()
                .map(UserPhraseDto::getPhraseDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns a comparator that compares map entries by the total read count of their associated list of UserPhraseDto.
     * The comparator compares two entries by summing the read counts of all UserPhraseDto in each entry's value list.
     *
     * @return a comparator for comparing map entries by total read count of UserPhraseDto lists
     */
    private static Comparator<Map.Entry<AuthorDto, List<UserPhraseDto>>> authorTotalViewsEntryComparator() {
        // Compare entries by the sum of read counts in their value list
        return Map.Entry.comparingByValue(
                Comparator.comparingInt(userPhrases ->
                        userPhrases.stream()
                                .mapToInt(UserPhraseDto::getReadCount)
                                .sum()
                ));
    }

    /**
     * Returns the first phrase from the list, or null if the list is empty.
     *
     * @param phrases list of PhraseDto
     * @return first PhraseDto or null
     */
    public static PhraseDto getFirstPhrase(List<PhraseDto> phrases) {
        return CollectionUtils.isEmpty(phrases) ? null : phrases.getFirst();
    }

    /**
     * Returns the author of the phrase, or a default message if not available.
     * The default message is defined in {@link PageConstants.StatisticsPage#FIELD_NOT_AVAILABLE_PLACEHOLDER}.
     *
     * @param phraseDto the PhraseDto
     * @return author name or FIELD_NOT_AVAILABLE
     */
    public static String getAuthor(PhraseDto phraseDto) {
        return phraseDto != null
                ? phraseDto.getAuthorFullName()
                : PageConstants.StatisticsPage.FIELD_NOT_AVAILABLE_PLACEHOLDER;
    }
}
