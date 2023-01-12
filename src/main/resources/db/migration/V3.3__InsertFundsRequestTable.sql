INSERT INTO funds_requests (`amount`,
                            `fk_user_id_of_request`,
                            `status`)

VALUES ('200',
        '5',
        'pending');
INSERT INTO funds_requests (`amount`,
                            `fk_user_id_of_request`,
                            `fk_manager_id_accepter`,
                            `status`)

VALUES ('200',
        '5',
        '4',
        'accept');
INSERT INTO funds_requests (`amount`,
                            `fk_user_id_of_request`,
                            `fk_manager_id_accepter`,
                            `status`)

VALUES ('200',
        '5',
        '4',
        'decline');
INSERT INTO funds_requests (`amount`,
                            `fk_user_id_of_request`,
                            `fk_manager_id_accepter`,
                            `status`)

VALUES ('200',
        '5',
        '4',
        'decline');