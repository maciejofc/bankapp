ALTER TABLE bank_transfer_fee_settings
    RENAME COLUMN transfer_express TO transfer_instant,
    RENAME COLUMN transfer_fast_one_min TO transfer_express_30seconds,
    RENAME COLUMN transfer_normal_two_min TO transfer_normal_one_min;