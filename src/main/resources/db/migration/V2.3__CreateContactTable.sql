CREATE table contacts
(
    name                     VARCHAR(30) NOT NULL,
    account_number           VARCHAR(16) NOT NULL,
    fk_user_contact_owner_id int         NOT NULL,
    CONSTRAINT fk_user_id_constraint_2 FOREIGN KEY (fk_user_contact_owner_id) REFERENCES users (id)


);