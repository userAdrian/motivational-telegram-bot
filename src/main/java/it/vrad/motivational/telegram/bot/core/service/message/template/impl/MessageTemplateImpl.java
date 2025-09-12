package it.vrad.motivational.telegram.bot.core.service.message.template.impl;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.message.template.util.MessageTemplateUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * Implementation of {@link MessageTemplate}
 * for generating localized message templates for bot pages and commands.
 */
@Slf4j
@Service
public class MessageTemplateImpl implements MessageTemplate {

    private final MessageSource messageSource;

    public MessageTemplateImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String propertyName, Object[] params, Locale locale) {
        return messageSource.getMessage(propertyName, params, locale);
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param name {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String generateInitialMessage(Locale locale, String name) {
        Objects.requireNonNull(locale);

        return getMessage(PageConstants.InitialPage.MESSAGE_PROPERTY, new Object[]{name}, locale);
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param phraseSendingTimes {@inheritDoc}
     * @param timeZone {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String generateInfoPageMessage(Locale locale, List<LocalTime> phraseSendingTimes, ZoneId timeZone) {
        return getMessage(
                PageConstants.InfoPage.MESSAGE_PROPERTY,
                MessageTemplateUtility.buildInfoPageMessageParamsArray(phraseSendingTimes, timeZone),
                locale
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param statisticsPageDetails {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String generateStatisticsPageMessage(Locale locale, StatisticsPageDetails statisticsPageDetails) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(statisticsPageDetails);

        return getMessage(
                PageConstants.StatisticsPage.MESSAGE_PROPERTY,
                MessageTemplateUtility.buildStatisticsPageMessageParameters(statisticsPageDetails),
                locale
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getAdminPageMessage(Locale locale) {
        return getMessage(
                PageConstants.AdminPage.MESSAGE_PROPERTY,
                null,
                locale
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getLoadFilePhrasesCommandMessage(Locale locale) {
        Objects.requireNonNull(locale);

        return getMessage(CommandConstants.LoadFilePhrases.MESSAGE_PROPERTY, null, locale);
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getLoadFilePhrasesCommandStepOneMessage(Locale locale) {
        Objects.requireNonNull(locale);

        return getMessage(CommandConstants.LoadFilePhrases.MESSAGE_PROPERTY_STEP_ONE, null, locale);
    }
}
