package pl.maciejowsky.bankapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferFormFilter {
    private boolean amountFilter;
    private boolean accountNumberFilter;
    private boolean dateFilter;

    private int fromAmount;
    private int toAmount;
    private String accountNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;




    public TransferFormFilter() {
    }


    public TransferFormFilter(boolean amountFilter, boolean accountNumberFilter, boolean dateFilter, int fromAmount, int toAmount, String accountNumber, LocalDate fromDate, LocalDate toDate) {
        this.amountFilter = amountFilter;
        this.accountNumberFilter = accountNumberFilter;
        this.dateFilter = dateFilter;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
        this.accountNumber = accountNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public boolean isDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(boolean dateFilter) {
        this.dateFilter = dateFilter;
    }

    public boolean isAmountFilter() {
        return amountFilter;
    }

    public void setAmountFilter(boolean amountFilter) {
        this.amountFilter = amountFilter;
    }

    public int getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(int fromAmount) {
        this.fromAmount = fromAmount;
    }

    public int getToAmount() {
        return toAmount;
    }

    public void setToAmount(int toAmount) {
        this.toAmount = toAmount;
    }

    public boolean isAccountNumberFilter() {
        return accountNumberFilter;
    }

    public void setAccountNumberFilter(boolean accountNumberFilter) {
        this.accountNumberFilter = accountNumberFilter;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
