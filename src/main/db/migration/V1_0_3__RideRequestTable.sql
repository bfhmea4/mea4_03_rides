CREATE TABLE ride_request (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title` TEXT,
    `description` TEXT,
    `user_id` INTEGER REFERENCES user(id)

);

INSERT INTO ride_request VALUES (1, 'first request', 'this is the first request', 1);