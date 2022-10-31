package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.BankProfitDAO;
import pl.maciejowsky.bankapp.dao.TransferDAO;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.ContactException;
import pl.maciejowsky.bankapp.exceptions.TransferException;
import pl.maciejowsky.bankapp.model.Contact;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.Transfer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferDAO transferDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    BankProfitDAO bankProfitDAO;


    @Override
    public boolean validateTransfer(BigDecimal moneyInvolved, BigDecimal balanceOfSender, String accountOfReceiver) throws TransferException {
        boolean receiverAccountExist = userAccountDAO.checkIfAccountNumberExist(accountOfReceiver);
        boolean isEnoughMoney = balanceOfSender.compareTo(moneyInvolved) >= 0;
        // 1 -> balance>involved
        // 0 -> balance == involved
        // -1 -> balance<involved
        if (!isEnoughMoney)
            throw new TransferException("Not enough money to make transfer");

        else if (!receiverAccountExist)
            throw new TransferException("Account number of receiver does not exist");
        else
            return true;
    }

    @Override
    public void makeTransfer(FormTransfer formTransfer) throws TransferException, ContactException {

        if (formTransfer.getAmount() == null)
            formTransfer.setAmount(BigDecimal.ZERO);
        BigDecimal moneyInvolvedInTransfer = BigDecimal.valueOf(formTransfer.getTransferType().getFee()).add(formTransfer.getAmount());
        BigDecimal balanceOfSender = userAccountDAO.getBalanceOfUserByAccountNumber(formTransfer.getFromAccount());

        if (validateTransfer(moneyInvolvedInTransfer, balanceOfSender, formTransfer.getToAccount())) {
            if (formTransfer.isAddToContact()) {


                int userIdContactOwner = userAccountDAO.getUserIdByAccountNumber(formTransfer.getFromAccount());
                addUserToContact(new Contact(formTransfer.getContactName(), formTransfer.getToAccount(), userIdContactOwner));
            }

            Timer timer = new Timer();
            int delayInMiliSec = formTransfer.getTransferType().getTimeOfSendingInMiliSec();
            Instant now = Instant.now();
            Instant receiveTime = now.plusMillis(delayInMiliSec);
            formTransfer.setSentAt(Timestamp.from(now).toString());
            formTransfer.setReceiveAt(Timestamp.from(receiveTime).toString());

            bankProfitDAO.addFeeValueToBankProfit(formTransfer.getTransferType().getFee());
            transferDAO.saveTransfer(formTransfer);
            userAccountDAO.modifyBalanceOnAccount(formTransfer.getFromAccount(), moneyInvolvedInTransfer, UserAccountDAO.AccountBalanceManipulation.WITHDRAW);


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("AFTER" + delayInMiliSec / 1000 + ",seconds i send " + formTransfer.getAmount());
                    userAccountDAO.modifyBalanceOnAccount(formTransfer.getToAccount(), formTransfer.getAmount(), UserAccountDAO.AccountBalanceManipulation.SUPPLY);
                    //notificationService.addTransferNotification(transfer.getAmount(),transfer.getToAccount(),receiverId);
                    timer.cancel();
                }
            }, delayInMiliSec);

        }

    }

    @Override
    public List<Contact> getContactsForUser(int userOwnerId) {
        return transferDAO.findUserContacts(userOwnerId);
    }


    @Override
    public List<Transfer> findTransferHistoryForUser(String username) {
//        int userId = userDAO.findUserByEmail(username).getId();
//        String accountNumber = userAccountDAO.getAccountNumberByUserId(userId);
//        return transferDAO.findTransfersForUser(accountNumber);
        return null;
    }

    public void addUserToContact(Contact contact) throws ContactException {

        List<Contact> userContacts = transferDAO.findUserContacts(contact.getContactOwnerId());

        boolean isTheSameName = userContacts.stream().anyMatch(c -> c.getName().equals(contact.getName()));
        boolean isTheSameAccountNumber = userContacts.stream().anyMatch(c -> c.getAccountNumber().equals(contact.getAccountNumber()));

        if (isTheSameName)
            throw new ContactException("You have already contact with this name, please try again with another name");

        else if (isTheSameAccountNumber)
            throw new ContactException("You have already contact with this account number, please unlock option with saving to contact list");
        else
            transferDAO.saveContact(contact);

    }

}
