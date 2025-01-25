CREATE TABLE IF NOT EXISTS expenses (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(255),
    description VARCHAR(255),
    amount DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    note VARCHAR(255),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
    );
