CREATE TABLE ride_request (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title` TEXT,
    `description` TEXT,
    `user_id` INTEGER REFERENCES user(id)

);
