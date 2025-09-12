package it.vrad.motivational.telegram.bot.core.model.factory;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.core.model.enums.PhraseCSVHeader;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.ChatType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.PhraseType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.ChatDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Chat;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import it.vrad.motivational.telegram.bot.infrastructure.util.CommonUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Factory class for creating persistence DTOs.
 * Provides static factory methods for constructing DTOs used in persistence operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistenceDtoFactory {

    /**
     * Builds a TelegramFileDto from a file name and message.
     *
     * @param fileName the name of the file
     * @param message  the Telegram message
     * @return a TelegramFileDto instance
     */
    public static TelegramFileDto buildTelegramFileDto(String fileName, Message message) {
        return new TelegramFileDto(fileName, MessageUtility.getPhotoId(message));
    }

    /**
     * Builds an UserDto for a new user with MEMBER role.
     *
     * @param telegramId the Telegram user ID
     * @param chat       the chat object
     * @return a UserDto instance
     */
    public static UserDto buildInitialUserDto(Long telegramId, Chat chat) {
        return buildUserDto(telegramId, UserRole.MEMBER, chat);
    }

    /**
     * Builds a UserDto with specified role and chat.
     *
     * @param telegramId the Telegram user ID
     * @param userRole   the user role
     * @param chat       the chat object
     * @return a UserDto instance
     */
    public static UserDto buildUserDto(Long telegramId, UserRole userRole, Chat chat) {
        Objects.requireNonNull(chat);

        return UserDto.builder()
                .telegramId(telegramId)
                .userRole(userRole)
                .chatDto(buildChatDto(chat.getId(), chat.getType()))
                .build();
    }

    /**
     * Builds a ChatDto from chat ID and type.
     *
     * @param chatId the chat ID
     * @param type   the chat type
     * @return a ChatDto instance
     */
    public static ChatDto buildChatDto(Long chatId, String type) {
        return ChatDto.builder()
                .telegramId(chatId)
                .type(ChatType.fromValue(type))
                .build();
    }

    /**
     * Builds an UserPhraseDto with read=true and readCount=1.
     *
     * @param userId   the user ID
     * @param phraseId the phrase ID
     * @return a UserPhraseDto instance
     */
    public static UserPhraseDto buildInitialUserPhraseDto(Long userId, Long phraseId) {
        return buildUserPhraseDto(userId, phraseId, Boolean.TRUE, 1);
    }

    /**
     * Builds a UserPhraseDto with specified read flag and read count.
     *
     * @param userId    the user ID
     * @param phraseId  the phrase ID
     * @param read      the read flag
     * @param readCount the read count
     * @return a UserPhraseDto instance
     */
    public static UserPhraseDto buildUserPhraseDto(Long userId, Long phraseId, Boolean read, Integer readCount) {
        return UserPhraseDto.builder()
                .id(new UserPhraseId())
                .userDto(UserDto.builder().id(userId).build())
                .phraseDto(PhraseDto.builder().id(phraseId).build())
                .read(read)
                .readCount(readCount)
                .build();
    }

    /**
     * Builds a CooldownDto for a user with the specified cooldown type.
     *
     * @param userDto    the user DTO
     * @param type       the cooldown type
     * @param endingDate the cooldown ending date
     * @return a CooldownDto instance
     */
    public static CooldownDto buildCooldownDto(UserDto userDto, CooldownType type, OffsetDateTime endingDate) {
        Objects.requireNonNull(type);

        return CooldownDto.builder()
                .type(type)
                .endingTime(endingDate)
                .userDto(UserDto.builder()
                        .id(userDto.getId()) //for saving, id is enough
                        .build())
                .build();
    }

    /**
     * Builds a PhraseDto from a CSV record.
     *
     * @param csvRecord the CSV record
     * @return a PhraseDto instance
     */
    public static PhraseDto buildPhraseDto(CSVRecord csvRecord) {
        return PhraseDto.builder()
                .author(buildAuthorDto(csvRecord))
                .text(CommonUtility.getElement(csvRecord, PhraseCSVHeader.TEXT))
                .type(PhraseType.valueOf(CommonUtility.getElement(csvRecord, PhraseCSVHeader.TYPE)))
                .build();
    }

    /**
     * Builds an AuthorDto from a CSV record.
     *
     * @param csvRecord the CSV record containing author data
     * @return an AuthorDto instance with first and last name set
     */
    private static AuthorDto buildAuthorDto(CSVRecord csvRecord) {
        return AuthorDto.builder()
                .firstName(CommonUtility.getElement(csvRecord, PhraseCSVHeader.AUTHOR_FIRST_NAME))
                .lastName(CommonUtility.getElement(csvRecord, PhraseCSVHeader.AUTHOR_LAST_NAME))
                .build();
    }

    /**
     * Builds a PhraseSentHistoryDto for a sent phrase.
     *
     * @param phraseDto   the phrase DTO
     * @param userDto     the user DTO
     * @param sendingDate the sending date
     * @return a PhraseSentHistoryDto instance
     */
    public static PhraseSentHistoryDto buildPhraseSentHistoryDto(PhraseDto phraseDto, UserDto userDto,
                                                                 OffsetDateTime sendingDate) {
        Objects.requireNonNull(phraseDto);
        Objects.requireNonNull(userDto);
        Objects.requireNonNull(sendingDate);

        return PhraseSentHistoryDto.builder()
                .phraseId(phraseDto.getId())
                .userId(userDto.getId())
                .sendingDate(sendingDate)
                .build();
    }
}
