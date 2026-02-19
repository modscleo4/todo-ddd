CREATE TYPE UserRole AS ENUM ('ADMIN', 'USER');

CREATE TABLE IF NOT EXISTS users
(
    id            UUID         NOT NULL PRIMARY KEY,
    version       INT          NOT NULL DEFAULT 0,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          UserRole     NOT NULL DEFAULT 'ADMIN',

    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS profiles
(
    id         UUID         NOT NULL PRIMARY KEY,
    version    INT          NOT NULL DEFAULT 0,
    user_id    UUID         NOT NULL REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE,
    name       VARCHAR(255) NOT NULL,
    cpf        VARCHAR(11)  NOT NULL,
    birth_date DATE         NOT NULL,

    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);
--
CREATE TABLE IF NOT EXISTS notes
(
    id         UUID         NOT NULL PRIMARY KEY,
    version    INT          NOT NULL DEFAULT 0,
    user_id    UUID         NOT NULL REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    done       BOOLEAN      NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);
--
CREATE TYPE TokenType AS ENUM ('ACCESS', 'REFRESH');

CREATE TABLE IF NOT EXISTS tokens
(
    id              UUID      NOT NULL PRIMARY KEY,
    version         INT       NOT NULL DEFAULT 0,
    user_id         UUID      NOT NULL REFERENCES users  ON UPDATE CASCADE ON DELETE CASCADE,
    access_token_id UUID               REFERENCES tokens ON UPDATE CASCADE ON DELETE CASCADE,
    type            TokenType NOT NULL,

    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP,
    expires_at      TIMESTAMP NOT NULL
);
