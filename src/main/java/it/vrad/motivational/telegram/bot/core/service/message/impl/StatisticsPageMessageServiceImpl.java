package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.StatisticsPageButton;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.StatisticsPageMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;


/**
 * Implementation of {@link StatisticsPageMessageService} for statistics page message operations.
 */
@Service
public class StatisticsPageMessageServiceImpl extends AbstractMessageService implements StatisticsPageMessageService {
    private final UserPhraseService userPhraseService;

    protected StatisticsPageMessageServiceImpl(
            TelegramFileDao telegramFileDao,
            MessageTemplate messageTemplate,
            PageConfigurationService pageConfigurationService,
            UserPhraseService userPhraseService
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService);
        this.userPhraseService = userPhraseService;
    }

    /**
     * {@inheritDoc}
     *
     * @param user {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto generateStatisticsMessageDto(UserDto user) {
        StatisticsPageDetails statisticsPageDetails = userPhraseService.fetchStatisticsPageDetails(user.getId());

        return buildMessageDto(
                PageConstants.StatisticsPage.MESSAGE_PHOTO_CLASSPATH,
                generateTextMessageSupplier(statisticsPageDetails),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, StatisticsPageButton.values(), user)
        );
    }

    private Supplier<String> generateTextMessageSupplier(StatisticsPageDetails statisticsPageDetails) {
        return () -> messageTemplate.generateStatisticsPageMessage(MessageConstants.DEFAULT_LOCALE, statisticsPageDetails);
    }

    /**
     * {@inheritDoc}
     *
     * @param sentMessage        {@inheritDoc}
     * @param telegramFileIdSent {@inheritDoc}
     */
    @Override
    public void persistTelegramFile(Message sentMessage, String telegramFileIdSent) {
        persistTelegramFile(PageConstants.StatisticsPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, telegramFileIdSent);
    }
}
