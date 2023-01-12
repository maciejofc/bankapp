CREATE TABLE deposits
(
    id              int primary key auto_increment,
    interest_rate   decimal(3, 2)  NOT NULL DEFAULT 1.00,
    time_in_minutes int            NOT NULL,
    deposit_amount  decimal(19, 2) NOT NULL,
    start_date      timestamp               DEFAULT CURRENT_TIMESTAMP(),
    fk_user_id      int            NOT NULL,
    status          enum ('started','canceled','ready','ended') DEFAULT 'started',
    money_to_withdraw    decimal(19, 2)          DEFAULT 0,
    CONSTRAINT fk_user_id_constraint_3 FOREIGN KEY (fk_user_id) REFERENCES users (id)
)

