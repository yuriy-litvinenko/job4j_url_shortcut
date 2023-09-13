create table IF NOT EXISTS convert
(
    id    serial primary key NOT NULL,
    url   text UNIQUE        NOT NULL,
    code  text UNIQUE,
    count numeric
);
