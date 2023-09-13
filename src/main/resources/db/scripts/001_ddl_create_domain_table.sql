create table IF NOT EXISTS domain
(
    id       serial primary key NOT NULL,
    site     text               NOT NULL UNIQUE,
    login    text               NOT NULL UNIQUE,
    password text               NOT NULL
);
