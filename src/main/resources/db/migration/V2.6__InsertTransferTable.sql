INSERT INTO transfers
(`from_account`,
 `to_account`,
 `amount`,
 `date_of_execution`,
 `date_of_receiving`,
 `transfer_type`)
VALUES ('0111111111111112',
        '0111111111111113',
        '1000',
        '2022-10-20 00:00:00',
        '2020-10-20 00:01:00',
        'normal');

INSERT INTO transfers
(`from_account`,
 `to_account`,
 `amount`,
 `date_of_execution`,
 `date_of_receiving`,
 `transfer_type`)
VALUES ('0111111111111113',
        '0111111111111112',
        '1000',
        '2022-10-20 01:00:00',
        '2020-10-20 01:01:00',
        'normal');

INSERT INTO transfers
(`from_account`,
 `to_account`,
 `amount`,
 `date_of_execution`,
 `date_of_receiving`,
 `transfer_type`)
VALUES ('0111111111111112',
        '0111111111111114',
        '1000',
        '2022-10-20 02:00:00',
        '2020-10-20 02:00:30',
        'express');