CREATE TABLE audit_trail
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    user_id   BIGINT NOT NULL,
    task      VARCHAR(255) NULL,
    timestamp datetime NULL,
    CONSTRAINT pk_audittrail PRIMARY KEY (id)
);

CREATE TABLE expense
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255) NULL,
    amount DOUBLE NOT NULL,
    status             VARCHAR(255) NULL,
    expensecategory_id BIGINT NOT NULL,
    user_id            BIGINT NOT NULL,
    due_date           date NULL,
    timestamp          date NULL,
    CONSTRAINT pk_expense PRIMARY KEY (id)
);

CREATE TABLE expense_category
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_expensecategory PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id INT    NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(20) NULL,
    email    VARCHAR(50) NULL,
    password VARCHAR(120) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_77584fbe74cc86922be2a3560 UNIQUE (username);

ALTER TABLE audit_trail
    ADD CONSTRAINT FK_AUDITTRAIL_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE expense
    ADD CONSTRAINT FK_EXPENSE_ON_EXPENSECATEGORY FOREIGN KEY (expensecategory_id) REFERENCES expense_category (id) ON DELETE CASCADE;

ALTER TABLE expense
    ADD CONSTRAINT FK_EXPENSE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);