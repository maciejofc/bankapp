package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.UserType;

import javax.validation.constraints.*;

public class BankDepositSettings {
    private int id;
    @Size(min = 3, max = 15)
    private String name;
    @Min(value = 1)
    @Max(value = 60)
    private int MinDepositTime;
    @DecimalMin(value="0.01")
    @DecimalMax(value="10.0")

    private double MinPercentageRate;
    @Min(value = 100)
    @Max(value = 10000)
    private int MinAmount;

    private UserType intendedFor;

    public BankDepositSettings() {
    }

    public BankDepositSettings(int id, String name, int minDepositTime, double minPercentageRate, int minAmount, UserType intendedFor) {
        this.id = id;
        this.name = name;
        MinDepositTime = minDepositTime;
        MinPercentageRate = minPercentageRate;
        MinAmount = minAmount;
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
        return MinDepositTime;
    }

    public void setMinDepositTime(int minDepositTime) {
        MinDepositTime = minDepositTime;
    }

    public double getMinPercentageRate() {
        return MinPercentageRate;
    }

    public void setMinPercentageRate(double minPercentageRate) {
        MinPercentageRate = minPercentageRate;
    }

    public int getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(int minAmount) {
        MinAmount = minAmount;
    }

    public UserType getIntendedFor() {
        return intendedFor;
    }

    public void setIntendedFor(UserType intendedFor) {
        this.intendedFor = intendedFor;
    }
}

