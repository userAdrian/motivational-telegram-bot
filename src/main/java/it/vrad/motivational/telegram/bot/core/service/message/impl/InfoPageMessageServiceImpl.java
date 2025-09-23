package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InfoPageButton;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.InfoPageMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Implementation of {@link InfoPageMessageService}
 * for info page message operations.
 */
@Component
public class InfoPageMessageServiceImpl extends AbstractMessageService implements InfoPageMessageService {
    private final PhraseProperties phraseProperties;

    protected InfoPageMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService,
            PhraseProperties phraseProperties
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService);
        this.phraseProperties = phraseProperties;
    }

    /**
     * {@inheritDoc}
     *
     * @param user {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto generateInfoMessageDto(UserDto user) {
        return buildMessageDto(
                PageConstants.InfoPage.MESSAGE_PHOTO_CLASSPATH,
                createTextMessageSupplier(),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, InfoPageButton.values(), user)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param sentMessage        {@inheritDoc}
     * @param telegramFileIdSent {@inheritDoc}
     */
    @Override
    public void persistTelegramFile(Message sentMessage, String telegramFileIdSent) {
        persistTelegramFile(PageConstants.InfoPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, telegramFileIdSent);
    }

    private Supplier<String> createTextMessageSupplier() {
        return () -> messageTemplate.generateInfoPageMessage(
                MessageConstants.DEFAULT_LOCALE, phraseProperties.getSendingTimes(), phraseProperties.getTimeZone());
    }
}
