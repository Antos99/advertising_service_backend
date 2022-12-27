DROP TABLE IF EXISTS addresses, advertisements, categories, roles, users, users_roles CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) UNIQUE,
    enabled  BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS roles
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, role_id),
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_role_id FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS advertisements(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    category_name VARCHAR(50),
    username VARCHAR(50) NOT NULL,
    price REAL,
    description VARCHAR(1000),
    created TIMESTAMP,
    expired TIMESTAMP,
    modified TIMESTAMP,
    active BOOLEAN,
    CONSTRAINT fk_category FOREIGN KEY(category_name) REFERENCES categories(name) ON UPDATE CASCADE,
    CONSTRAINT fk_username FOREIGN KEY(username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS addresses(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    advertisement_id BIGINT,
    voivodeship VARCHAR(50),
    city VARCHAR(50),
    post_code VARCHAR(50),
    street VARCHAR(50),
    CONSTRAINT fk_advertisement FOREIGN KEY(advertisement_id) REFERENCES advertisements(id) ON DELETE CASCADE
);

