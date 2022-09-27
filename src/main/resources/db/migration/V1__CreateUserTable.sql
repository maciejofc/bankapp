CREATE TABLE IF NOT EXISTS `users`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `full_name`   varchar(255),
    `birth_day`   date,
    `email`       varchar(255) NOT NULL,
    `password`    varchar(255) NOT NULL,
    `user_type`   ENUM ('regular','entrepreneur'),
    `created_at`  timestamp DEFAULT NOW(),
    `updated_at`  timestamp,
    `authorities` varchar(255),
    `roles`       varchar(255),
    `is_locked` boolean default false
);