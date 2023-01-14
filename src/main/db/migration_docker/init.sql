CREATE TABLE `user_table`
(
    `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(64),
    `last_name`  VARCHAR(64),
    `email`      VARCHAR(64) UNIQUE,
    `address`    VARCHAR(128),
    `password`   VARCHAR(255) NOT NULL
);

CREATE TABLE ride_request
(
    `id`              INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title`           TEXT,
    `description`     TEXT,
    `user_id`         INTEGER REFERENCES `user_Table` (id),
    `from_address_id` INTEGER REFERENCES address (id),
    `to_address_id`   INTEGER REFERENCES address (id),
    `start_time`      DATETIME


);

CREATE TABLE ride_offer
(
    `id`              INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title`           TEXT,
    `description`     TEXT,
    `user_id`         INTEGER REFERENCES `user_Table` (id),
    `from_address_id` INTEGER REFERENCES address (id),
    `to_address_id`   INTEGER REFERENCES address (id),
    `start_time`      DATETIME

);

CREATE TABLE address
(
    `id`           INTEGER PRIMARY KEY AUTO_INCREMENT,
    `street`       TEXT,
    `house_number` INTEGER,
    `postal_code`  INTEGER,
    `location`     TEXT
);
