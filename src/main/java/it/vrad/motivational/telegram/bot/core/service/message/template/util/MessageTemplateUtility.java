package it.vrad.motivational.telegram.bot.core.service.message.template.util;

import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.util.StatisticsPageUtility;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for building parameter arrays for message templates used in the bot.
 */
public class MessageTemplateUtility {

    /**
     * Builds an array of parameters for the info page message template.
     * The array contains the phrase sending times followed by the user's time zone.
     *
     * @param phraseSendingTimes list of times when phrases are sent
     * @param timeZone           the user's time zone
     * @return an array of parameters for the info page message
     */
    public static Object[] buildInfoPageMessageParamsArray(List<LocalTime> phraseSendingTimes, ZoneId timeZone) {
        List<Object> objects = new ArrayList<>(phraseSendingTimes);
        objects.add(timeZone);
        return objects.toArray();
    }

    /**
     * Builds an array of parameters for the statistics page message template.
     * The array contains the total number of phrases, the author of the most viewed phrase,
     * and the author of the least viewed phrase.
     *
     * @param pageDetails the statistics page details
     * @return an array of parameters for the statistics page message
     */
    public static Object[] buildStatisticsPageMessageParameters(StatisticsPageDetails pageDetails) {
        // Extract and return relevant statistics for the template
        return new Object[]{
                pageDetails.getTotalPhrases(),
                StatisticsPageUtility.getAuthor(pageDetails.getMostViewedPhrase()),
                StatisticsPageUtility.getAuthor(pageDetails.getLessViewedPhrase())
        };
    }
}
