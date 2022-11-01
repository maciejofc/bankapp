package pl.maciejowsky.bankapp.dao;

import java.math.BigDecimal;

public interface UserAccountDAO {
    int getUserIdByAccountNumber(String accountNumber);

    enum AccountBalanceManipulation {
        SUPPLY, WITHDRAW
    }

    BigDecimal getBalanceOfUserByAccountNumber(String accountNumber);

    boolean checkIfAccountNumberExist(String accountNumber);



    void modifyBalanceOnAccount(String accountNumber,BigDecimal moneyToManipulate, AccountBalanceManipulation accountBalanceManipulation);

    String getAccountNumberByUserId(int userId);
}
