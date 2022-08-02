CREATE TABLE IF NOT EXISTS categories(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS advertisements(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    category_id BIGINT,
    price REAL,
    description TEXT,
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

CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);