CREATE table transfers
(
    id                int AUTO_INCREMENT PRIMARY KEY,
    from_account      varchar(16)    NOT NULL,
    to_account        varchar(16)    NOT NULL,
    amount            decimal(19, 4) NOT NULL,
    date_of_execution timestamp DEFAULT NOW(),
    date_of_receiving timestamp,
    transfer_type     ENUM ('normal', 'express','instant') NOT NULL,
    CONSTRAINT fk_from_account_constraint FOREIGN KEY (from_account) REFERENCES users_account (account_number),
    CONSTRAINT fk_to_account_constraint FOREIGN KEY (to_account) REFERENCES users_account (account_number)
);