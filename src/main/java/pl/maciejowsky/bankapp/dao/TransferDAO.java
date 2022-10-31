package pl.maciejowsky.bankapp.dao;

import pl.maciejowsky.bankapp.model.Contact;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.Transfer;

import java.util.List;

public interface TransferDAO {
    Transfer getTransferInformation(int transferId);

    List<Transfer> findTransfersForUser(String accountNumber);

    void saveTransfer(FormTransfer transfer);
    List<Contact> findUserContacts(int userId);

    void saveContact(Contact contact);


    //boolean checkIfAccountExists(Transfer transfer);

}
