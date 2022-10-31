DELIMITER
$$
CREATE procedure fillUserAccounts()
BEGIN
	declare
value VARCHAR(16) default ' ';
	declare
num int default 0;
    	declare
i int default 1;
	SET
value = '111111111111111';
	SET
num = 5;
SET
i=1;
WHILE
(num<=25) DO
	SET value = CONCAT('0',value+1);
INSERT INTO users_account (account_number,balance,fk_user_id)
VALUES(value,1000,num);


SET
num = num+1;
SET
i = i+1;
END WHILE;
END $$
DELIMITER ;
call fillUserAccounts()