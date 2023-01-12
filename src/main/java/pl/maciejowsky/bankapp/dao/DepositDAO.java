package pl.maciejowsky.bankapp.dao;

import pl.maciejowsky.bankapp.model.Deposit;
import pl.maciejowsky.bankapp.model.enums.DepositStatus;

import java.math.BigDecimal;
import java.util.List;

public interface DepositDAO {

    List<Deposit> findDepositsForUser(int userId);

    Deposit findDepositById(int depositId);

    int saveDepositAndReturnDepositId(Deposit deposit);

    void changeDepositStatus(int depositId, DepositStatus depositStatus);

    void changeMoneyWithdraw(int depositId, BigDecimal moneyWithdraw);
}
