CREATE TABLE ride_offer
(
    `id`          INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title`       TEXT,
    `description` TEXT,
    `user_id`     INTEGER REFERENCES user (id)

);

INSERT INTO ride_offer VALUES (1, 'first offer', 'this is the first offer', 1);