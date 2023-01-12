package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.exceptions.DepositException;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.Deposit;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface DepositService {
    List<Deposit> getDepositHistoryForUser(int userId);

    void makeDeposit(Deposit deposit, String depositorAccountNumber) throws DepositException;


    List<Deposit> getActiveDepositsForUser(int userId);

    BigDecimal calculateMoneyAlreadyEarned(int userId);

    List<BankDepositSettings> listDepositVariants(Principal principal);

    boolean validateDeposit(Deposit deposit, String depositorAccountNumber) throws DepositException;



    void completeDeposit(int depositId,String depositorAccountNumber);
}
