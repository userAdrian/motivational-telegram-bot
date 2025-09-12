package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.StatisticsPage;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.StatisticsPageMessageService;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;


/**
 * Implementation of {@link StatisticsPageMessageService}
 * for statistics page message operations.
 */
@Service
public class StatisticsPageMessageServiceImpl extends AbstractMessageService implements StatisticsPageMessageService {
    private final UserPhraseService userPhraseService;

    protected StatisticsPageMessageServiceImpl(TelegramFileDao telegramFileDao, MessageTemplate messageTemplate,
                                               PageConfigurationService pageConfigurationService, UserPhraseService userPhraseService,
                                               TelegramIntegrationApi telegramIntegrationApi, UserService userService) {
        super(messageTemplate, telegramFileDao, pageConfigurationService, telegramIntegrationApi, userService);
        this.userPhraseService = userPhraseService;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto forwardToStatisticsPage(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Retrieve user
        UserDto user = getUserDto(incomingMessageContext, message);

        // Build and send statistics page message
        MessageDto messageDto = generateStatisticsMessageDto(user);
        Message sentMessage = telegramIntegrationApi.editMessageMedia(
                TelegramApiRequestUtility.getEditMessageMediaPhotoRequest(message, messageDto));

        // Persist Telegram file info after sending
        persistTelegramFile(PageConstants.StatisticsPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, messageDto.getTelegramFileId());

        return null;
    }

    private Supplier<String> generateTextMessageSupplier(StatisticsPageDetails statisticsPageDetails) {
        return () -> messageTemplate.generateStatisticsPageMessage(MessageConstants.DEFAULT_LOCALE, statisticsPageDetails);
    }

    private MessageDto generateStatisticsMessageDto(UserDto user) {
        StatisticsPageDetails statisticsPageDetails = userPhraseService.fetchStatisticsPageDetails(user.getId());

        return buildMessageDto(
                PageConstants.StatisticsPage.MESSAGE_PHOTO_CLASSPATH,
                generateTextMessageSupplier(statisticsPageDetails),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, StatisticsPage.values(), user)
        );
    }
}
