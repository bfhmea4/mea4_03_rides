ALTER TABLE ride_request
    ADD COLUMN from_address_id INTEGER REFERENCES address (id);
ALTER TABLE ride_request
    ADD COLUMN to_address_id INTEGER REFERENCES address (id);