ALTER TABLE user
    ADD UNIQUE (id),
    MODIFY COLUMN password VARCHAR(255);