CREATE TABLE ride_offer
(
    `id`          INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title`       TEXT,
    `description` TEXT,
    `user_id`     INTEGER REFERENCES `user_Table` (id)

);
