package pl.maciejowsky.bankapp.dao;

import java.math.BigDecimal;

public interface BankProfitDAO {

    int getBankProfit();

    void addFeeValueToBankProfit(int fee);

}
