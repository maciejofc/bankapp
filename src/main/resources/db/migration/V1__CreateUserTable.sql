CREATE TABLE  users
(
    `id`          int primary key auto_increment,
    `full_name`   varchar(255),
    `birth_day`   date,
    `email`       varchar(255) not null,
    `password`    varchar(255) not null,
    `user_type`   enum ('regular','entrepreneur'),
    `created_at`  timestamp default now(),
    `updated_at`  timestamp,
    `authorities` varchar(255),
    `roles`       varchar(255),
    `is_locked`   boolean   default false
);