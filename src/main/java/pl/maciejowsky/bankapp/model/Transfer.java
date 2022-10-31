package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.TransferType;

import java.math.BigDecimal;
import java.time.Instant;

public class Transfer {
    private int id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private TransferType transferType;
    private String sentAt;
    private String receiveAt;

    public Transfer(int id, String fromAccount, String toAccount, BigDecimal amount, TransferType transferType, String sentAt, String receiveAt) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transferType = transferType;
        this.sentAt = sentAt;
        this.receiveAt = receiveAt;
    }

    public Transfer(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getReceiveAt() {
        return receiveAt;
    }

    public void setReceiveAt(String receiveAt) {
        this.receiveAt = receiveAt;
    }

    public Transfer() {
    }
}
