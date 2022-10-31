package pl.maciejowsky.bankapp.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BankTransferSettings {
    private int id;
    @Min(value = 0)
    @Max(value = 20)
    private int transferInstant;


    @Min(value = 0)
    @Max(value = 20)
    private int transferExpress;

    @Min(value = 0)
    @Max(value = 20)
    private int transferNormal;

    private String createdAt;

    public BankTransferSettings(int id, int transferInstant, int transferExpress, int transferNormal, String createdAt) {
        this.id = id;
        this.transferInstant = transferInstant;
        this.transferExpress = transferExpress;
        this.transferNormal = transferNormal;
        this.createdAt = createdAt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferInstant() {
        return transferInstant;
    }

    public void setTransferInstant(int transferInstant) {
        this.transferInstant = transferInstant;
    }

    public int getTransferExpress() {
        return transferExpress;
    }

    public void setTransferExpress(int transferExpress) {
        this.transferExpress = transferExpress;
    }

    public int getTransferNormal() {
        return transferNormal;
    }

    public void setTransferNormal(int transferNormal) {
        this.transferNormal = transferNormal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BankTransferSettings() {
    }


}
