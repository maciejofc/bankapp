package pl.maciejowsky.bankapp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UserAccount {
    private int id;
    private String accountNumber;
    private BigDecimal balance;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserAccount(int id, String accountNumber, BigDecimal balance, int userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public UserAccount() {
    }
}
