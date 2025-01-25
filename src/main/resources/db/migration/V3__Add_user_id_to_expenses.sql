ALTER TABLE expenses ADD COLUMN user_id BIGINT;

ALTER TABLE expenses
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;
