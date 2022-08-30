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
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) UNIQUE,
    enabled  BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE roles
(
    id BIGSERIAL PRIMARY KEY,
    name  VARCHAR(50) NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGSERIAL NOT NULL,
    role_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_role_id FOREIGN KEY(role_id) REFERENCES roles(id)
)