package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.DepositDAO;
import pl.maciejowsky.bankapp.dao.UserAccountDAO;
import pl.maciejowsky.bankapp.dao.UserDAO;
import pl.maciejowsky.bankapp.exceptions.DepositException;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.Deposit;
import pl.maciejowsky.bankapp.model.enums.DepositStatus;
import pl.maciejowsky.bankapp.model.enums.UserType;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Service
public class DepositServiceImpl implements DepositService {
    @Autowired
    DepositDAO depositDAO;

    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    BankService bankService;

    @Autowired
    UserDAO userDAO;

    @Override
    public List<Deposit> getDepositHistoryForUser(int userId) {
        return depositDAO.findDepositsForUser(userId);
    }

    @Override
    public BigDecimal calculateMoneyAlreadyEarned(int userId) {
        return getDepositHistoryForUser(userId).stream()
                .filter(deposit -> deposit.getDepositStatus() == DepositStatus.ENDED)
                .map(deposit -> (deposit.getMoneyToWithdraw().subtract(deposit.getDepositAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    //Listing all deposit variants for user - regular or entrepreneur user
    public List<BankDepositSettings> listDepositVariants(Principal principal) {
        UserType userType = userDAO.findUserByEmail(principal.getName()).getUserType();
        return bankService.getSettings().getBankDepositSettings()
                .stream()
                .filter(bankDepositSettings -> bankDepositSettings.getIntendedFor() == userType)
                .collect(Collectors.toList());

    }

    @Override
    //Retrieving deposits of status STARTED and READY
    public List<Deposit> getActiveDepositsForUser(int userId) {
        List<Deposit> activeDeposits = depositDAO.findDepositsForUser(userId)
                .stream()
                .filter(deposit -> deposit.getDepositStatus() == DepositStatus.READY || deposit.getDepositStatus() == DepositStatus.STARTED)
                .collect(Collectors.toList());
        activeDeposits.forEach(deposit -> deposit.setMoneyToWithdraw(calculateMoneyToReturnEndedDeposit(deposit)));

        return activeDeposits;
    }


    // Checking if money of deposit is enougt to make deposit
    //  relatively to  balance and minimum condition
    @Override
    public boolean validateDeposit(Deposit deposit, String depositorAccountNumber) throws DepositException {
        Optional<BankDepositSettings> depositSetting = bankService.getSettings().getBankDepositSettings()
                .stream()
                .filter(bankDepositSettings -> bankDepositSettings.getId() == deposit.getDepositVariantId())
                .findFirst();

        BigDecimal balanceOfDepositor = userAccountDAO.getBalanceOfUserByAccountNumber(depositorAccountNumber);
        BigDecimal moneyWantToBeDeposited = deposit.getDepositAmount();
        BigDecimal minimalAmountOfDeposit = BigDecimal.valueOf(depositSetting.get().getMinAmount());

        boolean isEnoughMoneyInAccount = balanceOfDepositor.compareTo(moneyWantToBeDeposited) >= 0;
        boolean isEnoughMoneyToMinimumDeposit = minimalAmountOfDeposit.compareTo(moneyWantToBeDeposited) <= 0;
        if (!isEnoughMoneyInAccount)
            throw new DepositException("Not enough money to make deposit");
        else if (!isEnoughMoneyToMinimumDeposit)
            throw new DepositException("Please deposit minimum of selected variant");
        else
            return true;
    }


    //
    public void linkDataFromDepositVariantToDeposit(Deposit deposit) {
        Optional<BankDepositSettings> bankDepositSettings = bankService.getSettings().getBankDepositSettings().stream()
                .filter(bankDepositSettings1 -> bankDepositSettings1.getId() == deposit.getDepositVariantId())
                .findFirst();
        deposit.setInterestRate(bankDepositSettings.get().getPercentageRate());
        deposit.setDepositTime(bankDepositSettings.get().getMinDepositTime());
    }

    @Override
    public void makeDeposit(Deposit deposit, String depositorAccountNumber) throws DepositException {

        if (validateDeposit(deposit, depositorAccountNumber)) {
            // setting foreign key to save deposit - linking to user
            int depositorId = userAccountDAO.getUserIdByAccountNumber(depositorAccountNumber);
            deposit.setUserId(depositorId);

            linkDataFromDepositVariantToDeposit(deposit);
            Timer timer = new Timer();
            long timeOfDepositInSec = deposit.getDepositTime() * 60L;
            // 1.take money from acc
            // userAccountDAO.modifyBalanceOnAccount();
            userAccountDAO.modifyBalanceOnAccount(depositorAccountNumber, deposit.getDepositAmount(), UserAccountDAO.AccountBalanceManipulation.WITHDRAW);

            //2.Create deposit
            int depositId = depositDAO.saveDepositAndReturnDepositId(deposit);
            //3. after given time change status of deposit after given time
//
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //preventing from changing status when we canceled
                    boolean isDepositAlreadyCanceled = depositDAO.findDepositById(depositId).getDepositStatus() == DepositStatus.CANCELED;
                    if (isDepositAlreadyCanceled) {
                        timer.cancel();
                    } else {
                        depositDAO.changeDepositStatus(depositId, DepositStatus.READY);

                        timer.cancel();
                    }

                }
            }, timeOfDepositInSec * 1000);


        }
    }

    // calculating money to return  =  deposited + earned
    public BigDecimal calculateMoneyToReturnEndedDeposit(Deposit deposit) {
        Instant startDate = DateFormatter.stringToInstant(deposit.getStartDate());
        Instant endDate = Instant.now();
        long depositDurationInMinutes = Duration.between(startDate, endDate).toMinutes();
        BigDecimal initialAmount = deposit.getDepositAmount();
        BigDecimal interestRate = BigDecimal.valueOf(deposit.getInterestRate() / 100);
        //BigDecimal finalAmount = initialAmount * (1+interestRate)^depositDurationInMinutes
        BigDecimal adding = BigDecimal.valueOf(1).add(interestRate);
        BigDecimal powering = adding.pow((int) depositDurationInMinutes);
        BigDecimal multiplying = initialAmount.multiply(powering);
        BigDecimal finalAmount = multiplying;

        return finalAmount;

    }

    //Completing deposit -if the right time has passed - change status to ended and withdraw money
    // else change status to cancel and return only money deposited
    @Override
    public void completeDeposit(int depositId, String depositorAccountNumber) {
        Deposit madeDeposit = depositDAO.findDepositById(depositId);
        DepositStatus depositStatus = madeDeposit.getDepositStatus();
        if (depositStatus == DepositStatus.READY) {
            //supply money deposited + earned
            depositDAO.changeDepositStatus(depositId, DepositStatus.ENDED);
            BigDecimal moneyToWithdraw = calculateMoneyToReturnEndedDeposit(madeDeposit);
            userAccountDAO.modifyBalanceOnAccount(depositorAccountNumber, moneyToWithdraw, UserAccountDAO.AccountBalanceManipulation.SUPPLY);
            //setting profit
            depositDAO.changeMoneyWithdraw(depositId, moneyToWithdraw);
        } else {
            //supply money only deposited - withdraw == deposited
            BigDecimal moneyToWithdraw = madeDeposit.getDepositAmount();
            depositDAO.changeDepositStatus(depositId, DepositStatus.CANCELED);
            userAccountDAO.modifyBalanceOnAccount(depositorAccountNumber, moneyToWithdraw, UserAccountDAO.AccountBalanceManipulation.SUPPLY);
            //setting profit
            depositDAO.changeMoneyWithdraw(depositId, moneyToWithdraw);
        }

    }
}
