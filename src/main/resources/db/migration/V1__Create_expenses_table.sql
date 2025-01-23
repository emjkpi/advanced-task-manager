CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    amount NUMERIC NOT NULL,
    date DATE NOT NULL,
    note TEXT
);
