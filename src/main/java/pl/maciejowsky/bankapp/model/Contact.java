package pl.maciejowsky.bankapp.model;

public class Contact {

    private String name;
    private String accountNumber;
    private int contactOwnerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public Contact(String name, String accountNumber, int contactOwnerId) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.contactOwnerId = contactOwnerId;
    }

    public int getContactOwnerId() {
        return contactOwnerId;
    }
}
