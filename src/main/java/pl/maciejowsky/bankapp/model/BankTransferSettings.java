package pl.maciejowsky.bankapp.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BankTransferSettings {
    private int id;
    @Min(value = 0)
    @Max(value = 20)
    private int transferExpress;
    @Min(value = 0)
    @Max(value = 20)
    private int transferFast;
    @Min(value = 0)
    @Max(value = 20)
    private int transferNormal;
    @DecimalMin(value="0.0")
    @DecimalMax(value="50.0")
    private double entrepreneurDiscount;
    private String createdAt;

    public BankTransferSettings(int id, int transferExpress, int transferFast, int transferNormal, double entrepreneurDiscount, String createdAt) {
        this.id = id;
        this.transferExpress = transferExpress;
        this.transferFast = transferFast;
        this.transferNormal = transferNormal;
        this.entrepreneurDiscount = entrepreneurDiscount;
        this.createdAt = createdAt;
    }

    public int getTransferExpress() {
        return transferExpress;
    }

    public int getTransferFast() {
        return transferFast;
    }

    public int getTransferNormal() {
        return transferNormal;
    }

    public double getEntrepreneurDiscount() {
        return entrepreneurDiscount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransferExpress(int transferExpress) {
        this.transferExpress = transferExpress;
    }

    public void setTransferFast(int transferFast) {
        this.transferFast = transferFast;
    }

    public void setTransferNormal(int transferNormal) {
        this.transferNormal = transferNormal;
    }

    public void setEntrepreneurDiscount(double entrepreneurDiscount) {
        this.entrepreneurDiscount = entrepreneurDiscount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BankTransferSettings() {
    }
}
