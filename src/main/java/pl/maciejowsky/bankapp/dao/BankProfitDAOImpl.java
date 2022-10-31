package pl.maciejowsky.bankapp.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.TransferMapper;
import pl.maciejowsky.bankapp.model.Transfer;

import java.math.BigDecimal;
@Component
public class BankProfitDAOImpl implements BankProfitDAO {
    private final JdbcTemplate jdbcTemplate;

    public BankProfitDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getBankProfit() {
        String sql = "SELECT fee_profit FROM bank_profit";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void addFeeValueToBankProfit(int fee) {
        String sql = "UPDATE bank_profit SET fee_profit = fee_profit + ?";
        jdbcTemplate.update(sql, fee);
    }
}
