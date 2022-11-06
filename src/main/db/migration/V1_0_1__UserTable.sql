CREATE TABLE user (
    `id`            INTEGER PRIMARY KEY AUTO_INCREMENT,
    `first_name`    VARCHAR (64),
    `last_name`     VARCHAR (64),
    `email`         VARCHAR (64),
    `address`       VARCHAR (128),
    `password`      VARCHAR (32)
);

INSERT INTO user VALUES (1, 'first', 'user','first@user.com', 'Musterweg 28, 3084 Wabern', '1234');