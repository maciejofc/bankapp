package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.BankProfitDAO;
import pl.maciejowsky.bankapp.dao.TransferDAO;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.exceptions.ContactException;
import pl.maciejowsky.bankapp.exceptions.TransferException;
import pl.maciejowsky.bankapp.model.Contact;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.Transfer;
import pl.maciejowsky.bankapp.model.TransferFormFilter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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

    public void setRelationOfTransfers(List<Transfer> transfers, String accountNumberOfHistoryOwner) {
        transfers.stream().forEach(transfer -> {
            transfer.setSent(transfer.getFromAccount().equals(accountNumberOfHistoryOwner));
        });
    }


    @Override
    public List<Transfer> getTransferHistoryForUser(int userId, TransferFormFilter transferFormFilter) {
        String accountNumberOfHistoryOwner = userAccountDAO.getAccountNumberByUserId(userId);
        List<Transfer> transfersForUser = transferDAO.findTransfersForUser(accountNumberOfHistoryOwner);
        setRelationOfTransfers(transfersForUser, accountNumberOfHistoryOwner);
        List<Transfer> collect = transfersForUser.stream().filter(transfer -> transfer.isEligibleForPossibleFilters(transferFormFilter)).collect(Collectors.toList());
        return collect;
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


    public List<Integer> getActiveTransferYears(int userId) {
        Set<Integer> setOfYears = getTransferHistoryForUser(userId, new TransferFormFilter()).stream()
                .map(transfer -> Integer.valueOf(transfer.getReceiveAt().substring(0, 4)))
                .collect(Collectors.toSet());
        List<Integer> arr = new ArrayList<>(setOfYears);
        Collections.sort(arr, Collections.reverseOrder());
        return arr;
    }

    public List<List<List<Object>>> getChartData(int userId) {
        List<List<List<Object>>> dataChartWithAllYears = new ArrayList<>();
        List<Transfer> transferHistoryForUser = getTransferHistoryForUser(userId, new TransferFormFilter());


        List<Integer> activeYears = getActiveTransferYears(userId);

        for (int indexOfYear = 0; indexOfYear < activeYears.size(); indexOfYear++) {
            // years are sorted descending
            // 2022 - 0 index
            // 2021 - 1 index

            Integer activeTransferYear = activeYears.get(indexOfYear);

            //transfers only belonging to current active transfer year
            List<Transfer> dataForCurrentYear = transferHistoryForUser.stream()
                    //filtering by current year
                    .filter(transfer -> transfer.getReceiveAt().split("-")[0].equals(String.valueOf(activeTransferYear)))
                    .collect(Collectors.toList());


            dataForCurrentYear.forEach(transfer -> System.out.println(transfer.getReceiveAt()));
            //applying expenses and incomes to months in indexing year

            List<List<Object>> month12Chart = createEmpty12MonthlyChart();
            dataForCurrentYear.forEach(transfer -> {
                int numberOfMonth = Integer.valueOf(retrieveMonthNumberFromStringDate(transfer.getReceiveAt()));
                if (transfer.isSent()) {
                    BigDecimal previousExpensesInGivenMonth = (BigDecimal) month12Chart.get(numberOfMonth - 1).get(1);
                    BigDecimal previousAndCurrentlyExpensesInGivenMonth = previousExpensesInGivenMonth.add(transfer.getAmount());
                    month12Chart.get(numberOfMonth - 1).set(1, previousAndCurrentlyExpensesInGivenMonth);

                } else {
                    //
                    BigDecimal previousIncomeInGivenMonth = (BigDecimal) month12Chart.get(numberOfMonth - 1).get(2);
                    BigDecimal previousAndCurrentlyIncomesInGivenMonth = previousIncomeInGivenMonth.add(transfer.getAmount());
                    month12Chart.get(numberOfMonth - 1).set(2, previousAndCurrentlyIncomesInGivenMonth);
                }
            });
            //saving all 12 months to list of years
            dataChartWithAllYears.add(month12Chart);
        }

        return dataChartWithAllYears;
    }

    private List<List<Object>> createEmpty12MonthlyChart() {
        List<List<Object>> onlyMonthsChart = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            int monthNumber = i;
            List<Object> months = new ArrayList<>();
            // month index =0 in nested list
            months.add(0, monthNumber);
            // expenses = index 1 in nested list
            months.add(1, BigDecimal.ZERO);
            //incomes = index 2 in nested list
            months.add(2, BigDecimal.ZERO);

            onlyMonthsChart.add(months);
        }
        return onlyMonthsChart;
    }

    private String retrieveMonthNumberFromStringDate(String fullDate) {

        String onlyMonth = fullDate.split("-")[1];
        if (onlyMonth.startsWith("0"))
            return onlyMonth.substring(1, 2);
        return onlyMonth;
    }


}
