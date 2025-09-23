package it.vrad.motivational.telegram.bot.infrastructure.persistence.constants;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.ChatType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.PhraseType;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Test constants for persistence layer integration tests.
 * <p>
 * This class provides static constants for test data used in persistence tests, grouped by domain entity.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistenceTestConstants {

    // === General ===
    public static final String ID_FIELD_NAME_REGEX = "(^id$)|(^.*\\.id$)";
    public static final Long TELEGRAM_ID_NOT_PRESENT = 9999999999L;

    // === Phrase ===
    public static final Long PHRASE_ID = 1L;
    public static final PhraseType PHRASE_TYPE = PhraseType.BIOGRAPHY;
    public static final String PHRASE_TEXT = "phrase text";
    public static final PhraseType SAVE_PHRASE_TYPE = PhraseType.BIOGRAPHY;
    public static final String SAVE_PHRASE_TEXT = "save phrase text";
    public static final PhraseType SAVE_FIRST_PHRASE_TYPE = PhraseType.BIOGRAPHY;
    public static final String SAVE_FIRST_PHRASE_TEXT = "save first phrase text";

    // === Author ===
    public static final Long AUTHOR_ID = 1L;
    public static final String AUTHOR_FIRST_NAME = "Elon";
    public static final String AUTHOR_LAST_NAME = "Musk";
    public static final String SAVE_AUTHOR_FIRST_NAME = "Dwayne";
    public static final String SAVE_AUTHOR_LAST_NAME = "Johnson";

    // === User ===
    public static final Long USER_ID_WITHOUT_COOLDOWN = 2L;
    public static final Long USER_ID_NOT_PRESENT = 9999999L;
    public static final Long USER_ID = 1L;
    public static final Long USER_TELEGRAM_ID = 67890L;
    public static final UserRole USER_ROLE = UserRole.MEMBER;
    public static final Long SAVE_USER_TELEGRAM_ID = 73218L;
    public static final UserRole SAVE_USER_ROLE = UserRole.MEMBER;

    // === Chat ===
    public static final Long CHAT_TELEGRAM_ID = 22222L;
    public static final ChatType CHAT_TYPE = ChatType.PRIVATE;
    public static final Long SAVE_CHAT_TELEGRAM_ID = 4288821L;
    public static final ChatType SAVE_CHAT_TYPE = ChatType.PRIVATE;

    // === Telegram File ===
    public static final String TELEGRAM_FILE_NAME_NOT_PRESENT = "not_found";
    public static final String TELEGRAM_FILE_NAME = "first_test_file";
    public static final String TELEGRAM_FILE_ID = "tg_file_id_123";
    public static final String SAVE_TELEGRAM_FILE_NAME = "save_first_test_file";
    public static final String SAVE_TELEGRAM_FILE_ID = "save_tg_file_id_321111";

    // === Phrase Sent History ===
    public static final Long SAVE_PHRASE_SENT_HISTORY_PHRASE_ID = 100L;
    public static final Long SAVE_PHRASE_SENT_HISTORY_USER_ID = 200L;
    public static final OffsetDateTime SAVE_PHRASE_SENT_HISTORY_SENDING_DATE = OffsetDateTime.parse("2023-01-01T12:00:00+00:00");

    // === Cooldown ===
    public static final Long COOLDOWN_ID_NOT_PRESENT = 99999999L;
    public static final Long COOLDOWN_ID = 1L;
    public static final OffsetDateTime COOLDOWN_ENDING_TIME = OffsetDateTime.parse("2024-02-12T12:00:00+00:00");
    public static final OffsetDateTime SAVE_COOLDOWN_ENDING_TIME = OffsetDateTime.parse("2024-01-01T12:00:00+00:00");

    // === UserPhrase ===
    public static final UserPhraseId USER_PHRASE_ID = new UserPhraseId(1L, 1L);
    public static final UserPhraseId USER_PHRASE_ID_TO_SAVE = new UserPhraseId(2L, 2L);
}
