CREATE TABLE p_instructor (
    instructor_id  VARCHAR(36)   NOT NULL,
    user_id        VARCHAR(36)   NOT NULL,
    status         VARCHAR(20),
    bank_name      VARCHAR(30),
    account_number VARCHAR(25),
    account_holder VARCHAR(100),
    field          VARCHAR(20)   NOT NULL,
    bio            VARCHAR(500)  NOT NULL,
    career         VARCHAR(1000) NOT NULL,
    portfolio      VARCHAR(500)  NOT NULL,
    approved_at    TIMESTAMP,
    created_at     TIMESTAMP     NOT NULL,
    updated_at     TIMESTAMP     NOT NULL,
    deleted_at     TIMESTAMP,

    CONSTRAINT pk_p_instructor PRIMARY KEY (instructor_id),
    CONSTRAINT fk_p_instructor_user FOREIGN KEY (user_id) REFERENCES p_user (user_id),
    CONSTRAINT chk_p_instructor_status CHECK (status IN ('REQUESTED', 'APPROVED', 'REJECTED', 'SUSPENDED'))
);