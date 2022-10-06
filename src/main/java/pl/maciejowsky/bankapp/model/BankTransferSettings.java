package pl.maciejowsky.bankapp.model;

public class BankTransferSettings {
    private int id;
    private int transferExpress;
    private int transferFast;
    private double transferNormal;
    private double entrepreneurDiscount;
    private String createdAt;

    public BankTransferSettings(int id, int transferExpress, int transferFast, double transferNormal, double entrepreneurDiscount, String createdAt) {
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

    public double getTransferNormal() {
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

    public void setTransferNormal(double transferNormal) {
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
