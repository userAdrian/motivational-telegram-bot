-- ===========================
-- Users
-- ===========================
INSERT INTO "user" (telegram_id, user_role) VALUES (67890, 'MEMBER');
INSERT INTO "user" (telegram_id, user_role) VALUES (88711, 'MEMBER');
INSERT INTO "user" (telegram_id, user_role) VALUES (983218, 'KICKED');
INSERT INTO "user" (telegram_id, user_role) VALUES (93203, 'BANNED');
INSERT INTO "user" (telegram_id, user_role) VALUES (900003, 'ADMIN');

-- ===========================
-- Chats
-- ===========================
INSERT INTO chat (telegram_id, type, user_id) VALUES (22222, 'PRIVATE', 1);
INSERT INTO chat (telegram_id, type, user_id) VALUES (249118, 'PRIVATE', 2);
INSERT INTO chat (telegram_id, type, user_id) VALUES (73828, 'PRIVATE', 3);
INSERT INTO chat (telegram_id, type, user_id) VALUES (8818382, 'PRIVATE', 4);
INSERT INTO chat (telegram_id, type, user_id) VALUES (32166662, 'PRIVATE', 5);

-- ===========================
-- Cooldowns
-- ===========================
INSERT INTO cooldown (type, ending_time, user_id) VALUES ('RANDOM_PHRASE', '2024-02-12T12:00:00+00:00', 1);

-- ===========================
-- Authors
-- ===========================
INSERT INTO author (first_name, last_name) VALUES ('Elon', 'Musk');

-- ===========================
-- Phrases
-- ===========================
INSERT INTO phrase (type, text, author_id, disabled) VALUES ('BIOGRAPHY', 'phrase text', 1, false);
INSERT INTO phrase (type, text, author_id, disabled) VALUES ('BIOGRAPHY', 'Where is the bear''s honey?', 1, false);

-- ===========================
-- User phrases
-- ===========================
INSERT INTO user_phrase (user_id, phrase_id, read, read_count) VALUES (1, 1, 0, 2);

-- ===========================
-- Telegram Files
-- ===========================
INSERT INTO telegram_file (name, telegram_id) VALUES ('first_test_file', 'tg_file_id_123');
