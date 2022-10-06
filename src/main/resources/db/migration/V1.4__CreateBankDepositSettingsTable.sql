CREATE TABLE IF NOT EXISTS `bank_deposit_settings`
(
    `id`                      int primary key auto_increment,
    `name`                    varchar(200)                    default 'New deposit variant',
    `min_deposit_time_in_min` int                             default 1,
    `minute_percentage_rate`  decimal(3, 2)                   default 0.1,
    `min_amount`              int                             default 400,
    `intended_for`            enum ('regular','entrepreneur') default 'regular'
);