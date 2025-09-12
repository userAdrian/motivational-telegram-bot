package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.infrastructure.util.FilesUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Abstract base class for message services.
 * <p>
 * Provides common logic for building message DTOs, handling Telegram files, and user validation.
 */
public abstract class AbstractMessageService {
    protected final MessageTemplate messageTemplate;
    protected final TelegramFileDao telegramFileDao;
    protected final PageConfigurationService pageConfigurationService;
    protected final TelegramIntegrationApi telegramIntegrationApi;
    protected final UserService userService;


    protected AbstractMessageService(MessageTemplate messageTemplate, TelegramFileDao telegramFileDao,
                                     PageConfigurationService pageConfigurationService, TelegramIntegrationApi telegramIntegrationApi,
                                     UserService userService) {
        this.messageTemplate = messageTemplate;
        this.telegramFileDao = telegramFileDao;
        this.pageConfigurationService = pageConfigurationService;
        this.telegramIntegrationApi = telegramIntegrationApi;
        this.userService = userService;
    }

    /**
     * Builds a {@link MessageDto} using the provided file name, text supplier, and button supplier.
     *
     * @param fileName            the name of the photo file
     * @param textMessageSupplier supplier for the message text
     * @param buttonSupplier      supplier for the inline keyboard markup
     * @return the constructed MessageDto
     */
    protected MessageDto buildMessageDto(String fileName, Supplier<String> textMessageSupplier,
                                         Supplier<InlineKeyboardMarkup> buttonSupplier) {
        String telegramFileId = getTelegramFileId(fileName);

        return MessageDto.builder()
                .text(textMessageSupplier.get())
                .inlineKeyboardMarkup(buttonSupplier.get())
                .telegramFileId(telegramFileId)
                .photo(getPhotoFile(fileName, telegramFileId))
                .mediaType(getMediaType())
                .build();
    }

    /**
     * Retrieves the Telegram file ID for the given file name if a photo should be sent.
     *
     * @param fileName the name of the photo file
     * @return the Telegram file ID, or null if not applicable
     */
    protected String getTelegramFileId(String fileName) {
        if (shouldSendPhoto() && StringUtils.isNotBlank(fileName)) {
            return telegramFileDao.getTelegramIdByName(fileName);
        }

        return null;
    }

    /**
     * Gets the photo file for sending, if required and not already cached by Telegram.
     *
     * @param fileName       the name of the photo file
     * @param telegramFileId the Telegram file ID
     * @return the photo File object, or null if not needed
     */
    protected File getPhotoFile(String fileName, String telegramFileId) {
        if (shouldSendPhoto() && StringUtils.isBlank(telegramFileId)) {
            return FilesUtility.getFile(fileName);
        }

        return null;
    }

    /**
     * Returns the media type for the message, or null if not sending a photo.
     *
     * @return the media type string, or null
     */
    protected String getMediaType() {
        return shouldSendPhoto() ? TelegramConstants.TELEGRAM_PHOTO_MEDIA_TYPE_NAME : null;
    }

    /**
     * Determines whether a photo should be sent with the message.
     *
     * @return true if a photo should be sent, false otherwise
     */
    protected boolean shouldSendPhoto() {
        return true;
    }

    /**
     * Persists the Telegram file information if a photo was sent and not already cached by Telegram.
     *
     * @param photoName      the name of the photo file
     * @param sentMessage    the sent Telegram message
     * @param telegramFileId the Telegram file ID
     */
    protected void persistTelegramFile(String photoName, Message sentMessage, String telegramFileId) {
        if (shouldSendPhoto() && StringUtils.isBlank(telegramFileId)) {
            telegramFileDao.saveTelegramFile(PersistenceDtoFactory.buildTelegramFileDto(photoName, sentMessage));
        }
    }

    /**
     * Builds a supplier for {@link InlineKeyboardMarkup} using the provided locale, page values, and user.
     *
     * @param locale the locale for button labels
     * @param values the array of page enum values
     * @param user   the user for role-based button filtering
     * @return a supplier for InlineKeyboardMarkup
     */
    protected Supplier<InlineKeyboardMarkup> buildInlineKeyboardMarkup(Locale locale, PageEnum[] values, UserDto user) {
        return () -> new InlineKeyboardMarkup(pageConfigurationService.getPageButtons(locale, values, user));
    }

    /**
     * Retrieves the {@link UserDto} from the incoming message context.
     * <p>
     * Uses {@link UserDtoUtility#findValidUserIfAbsent(IncomingMessageContext, UserService, Message)}
     * to find or validate the user.
     *
     * @param incomingMessageContext the context of the incoming message
     * @param message                the Telegram message
     * @return the UserDto for the message sender
     */
    protected UserDto getUserDto(IncomingMessageContext incomingMessageContext, Message message) {
        return UserDtoUtility.findValidUserIfAbsent(incomingMessageContext, userService, message);
    }

    /**
     * Validates the user and retrieves the {@link UserDto} for the given roles.
     * <p>
     * See {@link UserDtoUtility#validateUserAuthorization(String, UserDto, Set)} for user authorization
     *
     * @param incomingMessageContext the context of the incoming message
     * @param userRoles              the set of allowed user roles
     * @return the validated UserDto
     */
    protected UserDto validateAndGetUserDto(IncomingMessageContext incomingMessageContext, Set<UserRole> userRoles) {
        Message message = incomingMessageContext.getMessageSent();
        // find valid user
        UserDto user = getUserDto(incomingMessageContext, message);

        String command = MessageUtility.resolveCommandIfPresent(message.getText());

        return UserDtoUtility.validateUserAuthorization(command, user, userRoles);
    }

}
