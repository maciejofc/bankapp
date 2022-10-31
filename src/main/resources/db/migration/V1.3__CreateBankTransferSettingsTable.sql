CREATE TABLE  bank_transfer_fee_settings
(
    `id`                                      int primary key auto_increment,
    `transfer_express`                        int       default 15,
    `transfer_fast_one_min`                   int       default 5,
    `transfer_normal_two_min`                 int       default 0,
    `created_at`                              timestamp default now()
);