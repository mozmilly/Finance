ALTER TABLE users
    ADD account_non_locked BIT(1) NULL;

ALTER TABLE users
    ADD failed_attempt INT NULL;

ALTER TABLE users
    ADD lock_time datetime NULL;
