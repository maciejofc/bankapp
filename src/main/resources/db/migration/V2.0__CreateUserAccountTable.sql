CREATE table users_account
(
    id             int AUTO_INCREMENT PRIMARY KEY,
    account_number varchar(16) NOT NULL UNIQUE ,
    balance        DECIMAL(19, 2) DEFAULT 10000,
    fk_user_id     int         NOT NULL,
    CONSTRAINT fk_user_id_constraint FOREIGN KEY (fk_user_id) REFERENCES users (id)

);