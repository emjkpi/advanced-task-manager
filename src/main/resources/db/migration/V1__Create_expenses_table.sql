CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(255),
    amount DOUBLE PRECISION,
    date DATE,
    note VARCHAR(255)
);
