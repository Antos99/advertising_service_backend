DROP TABLE IF EXISTS categories, advertisements CASCADE;

CREATE TABLE categories(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE advertisements(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    category_id BIGINT,
    price REAL,
    description TEXT,
    voivodeship TEXT,
    city TEXT,
    street TEXT,
    created TIMESTAMP,
    expired TIMESTAMP,
    modified TIMESTAMP,
    active BOOLEAN,
    CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS addresses(
    id BIGSERIAL PRIMARY KEY,
    advertisement_id BIGSERIAL,
    voivodeship TEXT,
    city TEXT,
    post_code TEXT,
    street TEXT,
    CONSTRAINT fk_advertisement FOREIGN KEY(advertisement_id) REFERENCES advertisements(id)
);