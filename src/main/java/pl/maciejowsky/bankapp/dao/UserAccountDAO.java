package pl.maciejowsky.bankapp.dao;

import pl.maciejowsky.bankapp.model.UserAccount;

import java.math.BigDecimal;

public interface UserAccountDAO {
    int getUserIdByAccountNumber(String accountNumber);

    UserAccount getUserAccountById(int userId);

    enum AccountBalanceManipulation {
        SUPPLY, WITHDRAW
    }

    BigDecimal getBalanceOfUserByAccountNumber(String accountNumber);

    void saveUserAccount(int userId);

    boolean checkIfAccountNumberExist(String accountNumber);

    void modifyBalanceOnAccount(String accountNumber,BigDecimal moneyToManipulate, AccountBalanceManipulation accountBalanceManipulation);

    String getAccountNumberByUserId(int userId);
}
