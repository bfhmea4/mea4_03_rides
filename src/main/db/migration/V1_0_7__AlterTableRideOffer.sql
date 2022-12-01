ALTER TABLE ride_offer
    ADD COLUMN from_address_id INTEGER REFERENCES address (id);
ALTER TABLE ride_offer
    ADD COLUMN to_address_id INTEGER REFERENCES address (id);