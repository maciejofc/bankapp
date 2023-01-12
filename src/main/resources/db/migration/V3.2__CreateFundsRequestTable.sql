CREATE TABLE funds_requests
(
    id                     int PRIMARY KEY auto_increment,
    amount                 int NOT NULL,
    fk_user_id_of_request  int NOT NULL,
    fk_manager_id_accepter int DEFAULT NULL,
    status                 enum('accept','decline','pending') DEFAULT 'pending',

    CONSTRAINT fk_user_id_constraint_4 FOREIGN KEY (fk_user_id_of_request) REFERENCES users (id),
    CONSTRAINT fk_user_id_constraint_5 FOREIGN KEY (fk_manager_id_accepter) REFERENCES users (id)
)

