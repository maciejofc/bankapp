package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.TransferType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transfer {
    private int id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private TransferType transferType;
    private String sentAt;
    private String receiveAt;

    private boolean isSent;

    public boolean isEligibleForPossibleFilters(TransferFormFilter transferFormFilter) {
        //if filter is not applied it stay true and then it always passes the stream filtering
        //
        boolean amountExpression = true;
        boolean numberExpression = true;
        boolean dateExpression = true;
        if (transferFormFilter.isAmountFilter()) {
            BigDecimal fromRange = BigDecimal.valueOf(transferFormFilter.getFromAmount());
            BigDecimal toRange = BigDecimal.valueOf(transferFormFilter.getToAmount());
            amountExpression = fromRange.compareTo(amount) <= 0 && toRange.compareTo(amount) >= 0;
        }
        if (transferFormFilter.isAccountNumberFilter()) {
            numberExpression = getToAccount().equals(transferFormFilter.getAccountNumber());
        }
        if (transferFormFilter.isDateFilter()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String onlyDate = receiveAt.split(" ")[0];
            LocalDate receivedAt = LocalDate.parse(onlyDate, formatter);
            dateExpression = transferFormFilter.getFromDate().compareTo(receivedAt) <= 0 && transferFormFilter.getToDate().compareTo(receivedAt) >= 0;

        }

        return amountExpression && numberExpression && dateExpression;

    }

    public Transfer(int id, String fromAccount, String toAccount, BigDecimal amount, TransferType transferType, String sentAt, String receiveAt) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transferType = transferType;
        this.sentAt = sentAt;
        this.receiveAt = receiveAt;
    }


    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
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
