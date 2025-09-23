package it.vrad.motivational.telegram.bot.infrastructure.persistence;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.ChatDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.ChatType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.AUTHOR_FIRST_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.AUTHOR_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.AUTHOR_LAST_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.CHAT_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.CHAT_TYPE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.COOLDOWN_ENDING_TIME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.PHRASE_TEXT;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.PHRASE_TYPE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_AUTHOR_FIRST_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_AUTHOR_LAST_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_CHAT_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_CHAT_TYPE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_COOLDOWN_ENDING_TIME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_FIRST_PHRASE_TEXT;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_FIRST_PHRASE_TYPE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_PHRASE_SENT_HISTORY_PHRASE_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_PHRASE_SENT_HISTORY_SENDING_DATE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_PHRASE_SENT_HISTORY_USER_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_PHRASE_TEXT;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_PHRASE_TYPE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_TELEGRAM_FILE_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_TELEGRAM_FILE_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_USER_ROLE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.SAVE_USER_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.TELEGRAM_FILE_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.TELEGRAM_FILE_NAME;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_ID_WITHOUT_COOLDOWN;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_PHRASE_ID_TO_SAVE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_ROLE;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_TELEGRAM_ID;

/**
 * Factory for creating test DTOs and IDs for persistence layer integration tests.
 * <p>
 * Provides static builder methods for test data, grouped by domain entity.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistenceTestFactory {
    // === User ===
    public static UserDto createGenericUserDto() {
        return UserDto.builder()
                .telegramId(USER_TELEGRAM_ID)
                .userRole(USER_ROLE)
                .chatDto(createChatDto(CHAT_TELEGRAM_ID, CHAT_TYPE))
                .build();
    }

    public static UserDto createUserDtoToSave() {
        return UserDto.builder()
                .telegramId(SAVE_USER_TELEGRAM_ID)
                .userRole(SAVE_USER_ROLE)
                .chatDto(createChatDto(SAVE_CHAT_TELEGRAM_ID, SAVE_CHAT_TYPE))
                .build();
    }

    public static UserDto createUserDtoToUpdate() {
        return UserDto.builder()
                .userRole(UserRole.ADMIN)
                .build();
    }

    // === Chat ===
    public static ChatDto createChatDto(Long telegramId, ChatType chatType) {
        return ChatDto.builder()
                .telegramId(telegramId)
                .type(chatType)
                .build();
    }

    // === Phrase ===
    public static PhraseDto createGenericPhraseDto() {
        return PhraseDto.builder()
                .type(PHRASE_TYPE)
                .text(PHRASE_TEXT)
                .disabled(false)
                .author(createGenericAuthorDto())
                .build();
    }

    public static Set<PhraseDto> createPhraseDtosToSave() {
        return Set.of(
                createPhraseDtoToSave(),
                createFirstPhraseDtoToSave()
        );
    }

    public static PhraseDto createPhraseDtoToSave() {
        return PhraseDto.builder()
                .type(SAVE_PHRASE_TYPE)
                .text(SAVE_PHRASE_TEXT)
                .disabled(false)
                .author(createGenericAuthorDto())
                .build();
    }

    public static PhraseDto createFirstPhraseDtoToSave() {
        return PhraseDto.builder()
                .type(SAVE_FIRST_PHRASE_TYPE)
                .text(SAVE_FIRST_PHRASE_TEXT)
                .disabled(false)
                .author(createGenericAuthorDto())
                .build();
    }

    // === Author ===
    public static AuthorDto createGenericAuthorDto() {
        return AuthorDto.builder()
                .id(AUTHOR_ID)
                .firstName(AUTHOR_FIRST_NAME)
                .lastName(AUTHOR_LAST_NAME)
                .build();
    }

    public static AuthorDto createAuthorDtoToSave() {
        return AuthorDto.builder()
                .firstName(SAVE_AUTHOR_FIRST_NAME)
                .lastName(SAVE_AUTHOR_LAST_NAME)
                .build();
    }

    // === Telegram File ===
    public static TelegramFileDto createGenericTelegramFileDto() {
        return new TelegramFileDto(TELEGRAM_FILE_NAME, TELEGRAM_FILE_ID);
    }

    public static TelegramFileDto createTelegramFileDtoToSave() {
        return new TelegramFileDto(SAVE_TELEGRAM_FILE_NAME, SAVE_TELEGRAM_FILE_ID);
    }

    // === Phrase Sent History ===
    public static PhraseSentHistoryDto createPhraseSentHistoryDtoToSave() {
        return PhraseSentHistoryDto.builder()
                .phraseId(SAVE_PHRASE_SENT_HISTORY_PHRASE_ID)
                .userId(SAVE_PHRASE_SENT_HISTORY_USER_ID)
                .sendingDate(SAVE_PHRASE_SENT_HISTORY_SENDING_DATE)
                .build();
    }

    // === Cooldown ===
    public static CooldownDto createGenericCooldownDto(CooldownType type) {
        return CooldownDto.builder()
                .type(type)
                .endingTime(COOLDOWN_ENDING_TIME)
                .userDto(createGenericUserDto())
                .build();
    }

    public static CooldownDto createCooldownDtoToSave(CooldownType type) {
        return CooldownDto.builder()
                .type(type)
                .endingTime(SAVE_COOLDOWN_ENDING_TIME)
                .userDto(UserDto.builder().id(USER_ID_WITHOUT_COOLDOWN).build())
                .build();
    }

    // === UserPhrase ===
    public static UserPhraseDto createGenericUserPhraseDto() {
        return UserPhraseDto.builder()
                .read(false)
                .readCount(2)
                .userDto(createGenericUserDto())
                .phraseDto(createGenericPhraseDto())
                .build();
    }

    public static UserPhraseDto createUserPhraseDtoToSave() {
        return UserPhraseDto.builder()
                .id(new UserPhraseId())
                .read(true)
                .readCount(1)
                .userDto(UserDto.builder().id(USER_PHRASE_ID_TO_SAVE.getUserId()).build())
                .phraseDto(PhraseDto.builder().id(USER_PHRASE_ID_TO_SAVE.getPhraseId()).build())
                .build();
    }
}
