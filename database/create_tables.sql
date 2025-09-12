-- Table: user
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL UNIQUE,
    user_role VARCHAR(32) NOT NULL
);

-- Table: chat
CREATE TABLE chat (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL,
    type VARCHAR(32) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_chat_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

-- Table: author
CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

-- Create index on author first_name and last_name for faster lookup
CREATE INDEX idx_author_first_last_name ON author(first_name, last_name);

-- Table: phrase
CREATE TABLE phrase (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    text VARCHAR(1024) NOT NULL,
    type VARCHAR(32) NOT NULL,
    disabled BOOLEAN,
    CONSTRAINT fk_phrase_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

-- Table: telegram_file
CREATE TABLE telegram_file (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    telegram_id VARCHAR(255) NOT NULL
);

-- Table: cooldown
CREATE TABLE cooldown (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(32) NOT NULL,
    ending_time TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_cooldown_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

-- Table: user_phrase
CREATE TABLE user_phrase (
    user_id BIGINT NOT NULL,
    phrase_id BIGINT NOT NULL,
    read BOOLEAN NOT NULL,
    read_count INTEGER,
    PRIMARY KEY (user_id, phrase_id),
    CONSTRAINT fk_userphrase_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_userphrase_phrase FOREIGN KEY (phrase_id) REFERENCES phrase(id) ON DELETE CASCADE
);

-- Table: phrase_sent_history
CREATE TABLE phrase_sent_history (
    id BIGSERIAL PRIMARY KEY,
    phrase_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    sending_date TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Indexes for performance (optional)
CREATE INDEX idx_cooldown_user_id ON cooldown(user_id);
CREATE INDEX idx_user_phrase_user_id ON user_phrase(user_id);
CREATE INDEX idx_user_phrase_phrase_id ON user_phrase(phrase_id);
CREATE INDEX idx_phrase_sent_history_user_id ON phrase_sent_history(user_id);
CREATE INDEX idx_phrase_sent_history_phrase_id ON phrase_sent_history(phrase_id);
