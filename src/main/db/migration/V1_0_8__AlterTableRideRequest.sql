ALTER TABLE ride_request
    ADD COLUMN from_address_id INTEGER REFERENCES address (id),
    ADD COLUMN to_address_id INTEGER REFERENCES address (id);