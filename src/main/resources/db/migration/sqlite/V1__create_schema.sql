CREATE TABLE IF NOT EXISTS users (
    id            TEXT      NOT NULL PRIMARY KEY,
    version       INT       NOT NULL DEFAULT 0,
    email         TEXT      NOT NULL UNIQUE,
    password_hash TEXT      NOT NULL,
    role          TEXT      NOT NULL DEFAULT 'ADMIN' CHECK (role IN ('ADMIN', 'USER')),

    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS profiles (
    id         TEXT        NOT NULL PRIMARY KEY,
    version    INT         NOT NULL DEFAULT 0,
    user_id    TEXT        NOT NULL REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE,
    name       TEXT        NOT NULL,
    cpf        TEXT        NOT NULL,
    birth_date DATE        NOT NULL,

    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP
);
--
CREATE TABLE IF NOT EXISTS notes (
    id         TEXT      NOT NULL PRIMARY KEY,
    version    INT       NOT NULL DEFAULT 0,
    user_id    TEXT      NOT NULL REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE,
    title      TEXT      NOT NULL,
    content    TEXT      NOT NULL,
    done       BOOLEAN   NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
--
CREATE TABLE IF NOT EXISTS tokens (
    id              TEXT      NOT NULL PRIMARY KEY,
    version         INT       NOT NULL DEFAULT 0,
    user_id         TEXT      NOT NULL REFERENCES users  ON UPDATE CASCADE ON DELETE CASCADE,
    access_token_id TEXT               REFERENCES tokens ON UPDATE CASCADE ON DELETE CASCADE,
    type            TEXT      NOT NULL CHECK (type IN ('ACCESS', 'REFRESH')),

    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP,
    expires_at      TIMESTAMP NOT NULL
);
