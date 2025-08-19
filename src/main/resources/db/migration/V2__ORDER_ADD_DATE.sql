ALTER TABLE db_order
    ADD date date;

UPDATE db_order
SET date = '1970-01-01'
WHERE date IS NULL;
ALTER TABLE db_order
    ALTER COLUMN date SET NOT NULL;