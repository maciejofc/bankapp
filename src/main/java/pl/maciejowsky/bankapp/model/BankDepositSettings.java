package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.UserType;

import javax.validation.constraints.*;

public class BankDepositSettings {
    private int id;
    @Size(min = 3, max = 15)
    private String name;
    @Min(value = 1)
    @Max(value = 60)
    private int minDepositTime;
    @DecimalMin(value="0.01")
    @DecimalMax(value="10.0")

    private double percentageRate;
    @Min(value = 100)
    @Max(value = 10000)
    private int minAmount;

    private UserType intendedFor;

    public BankDepositSettings() {
    }

    public BankDepositSettings(int id, String name, int minDepositTime, double percentageRate, int minAmount, UserType intendedFor) {
        this.id = id;
        this.name = name;
        this.minDepositTime = minDepositTime;
        this.percentageRate = percentageRate;
        this.minAmount = minAmount;
        this.intendedFor = intendedFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinDepositTime() {
        return minDepositTime;
    }

    public void setMinDepositTime(int minDepositTime) {
        this.minDepositTime = minDepositTime;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public UserType getIntendedFor() {
        return intendedFor;
    }

    public void setIntendedFor(UserType intendedFor) {
        this.intendedFor = intendedFor;
    }
}

