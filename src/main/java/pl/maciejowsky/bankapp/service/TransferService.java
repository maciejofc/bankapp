package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.exceptions.ContactException;
import pl.maciejowsky.bankapp.exceptions.TransferException;
import pl.maciejowsky.bankapp.model.Contact;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.Transfer;
import pl.maciejowsky.bankapp.model.TransferFormFilter;

import java.math.BigDecimal;
import java.util.List;

public interface TransferService {

    List<Transfer> getTransferHistoryForUser(int userId, TransferFormFilter transferFormFilter);

    void addUserToContact(Contact contact) throws ContactException;

    boolean validateTransfer(BigDecimal moneyInvolved, BigDecimal balanceOfSender, String accountOfReceiver) throws TransferException;

    void makeTransfer(FormTransfer formTransfer) throws TransferException, ContactException;

    List<Contact> getContactsForUser(int userOwnerId);

}
