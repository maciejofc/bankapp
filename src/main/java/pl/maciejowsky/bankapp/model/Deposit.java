package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.DepositStatus;

import java.math.BigDecimal;

public class Deposit {
    private int id;
    private double interestRate;
    private int depositTime;
    private BigDecimal depositAmount;
    private String startDate;
    private int userId;
    private DepositStatus depositStatus;

    private BigDecimal moneyToWithdraw;

    private int depositVariantId;

    public int getDepositVariantId() {
        return depositVariantId;
    }

    public void setDepositVariantId(int depositVariantId) {
        this.depositVariantId = depositVariantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(int depositTime) {
        this.depositTime = depositTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DepositStatus getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(DepositStatus depositStatus) {
        this.depositStatus = depositStatus;
    }

    public BigDecimal getDepositAmount() {

        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getMoneyToWithdraw() {

        return moneyToWithdraw;
    }

    public void setMoneyToWithdraw(BigDecimal moneyToWithdraw) {
        this.moneyToWithdraw = moneyToWithdraw;
    }

    public Deposit() {
    }

    public Deposit(int id, double interestRate, int depositTime, BigDecimal depositAmount, String startDate, int userId, DepositStatus depositStatus, BigDecimal moneyToWithdraw) {
        this.id = id;
        this.interestRate = interestRate;
        this.depositTime = depositTime;
        this.depositAmount = depositAmount;
        this.startDate = startDate;
        this.userId = userId;
        this.depositStatus = depositStatus;
        this.moneyToWithdraw = moneyToWithdraw;
    }

}
