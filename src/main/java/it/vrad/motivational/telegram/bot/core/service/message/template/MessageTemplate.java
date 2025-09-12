package it.vrad.motivational.telegram.bot.core.service.message.template;

import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

/**
 * Service interface for generating localized message templates for various bot pages and commands.
 */
public interface MessageTemplate {

    /**
     * Generates the initial page message for a user.
     *
     * @param locale the user's locale
     * @param name   the user's name
     * @return the formatted initial message
     */
    String generateInitialMessage(Locale locale, String name);

    /**
     * Generates the info page message, including phrase sending times and time zone.
     *
     * @param locale             the user's locale
     * @param phraseSendingTimes list of times when phrases are sent
     * @param timeZone           the user's time zone
     * @return the formatted info page message
     */
    String generateInfoPageMessage(Locale locale, List<LocalTime> phraseSendingTimes, ZoneId timeZone);

    /**
     * Generates the statistics page message with user statistics.
     *
     * @param locale                the user's locale
     * @param statisticsPageDetails the statistics details to display
     * @return the formatted statistics page message
     */
    String generateStatisticsPageMessage(Locale locale, StatisticsPageDetails statisticsPageDetails);

    /**
     * Returns the admin page message.
     *
     * @param locale the user's locale
     * @return the formatted admin page message
     */
    String getAdminPageMessage(Locale locale);

    /**
     * Returns the message for the load file phrases command.
     *
     * @param locale the user's locale
     * @return the formatted load file phrases command message
     */
    String getLoadFilePhrasesCommandMessage(Locale locale);

    /**
     * Returns the step one message for the load file phrases command.
     *
     * @param locale the user's locale
     * @return the formatted step one message for the load file phrases command
     */
    String getLoadFilePhrasesCommandStepOneMessage(Locale locale);

}
