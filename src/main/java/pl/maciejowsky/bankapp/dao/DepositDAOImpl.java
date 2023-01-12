package pl.maciejowsky.bankapp.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.DepositMapper;
import pl.maciejowsky.bankapp.model.Deposit;
import pl.maciejowsky.bankapp.model.enums.DepositStatus;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DepositDAOImpl implements DepositDAO {

    private final JdbcTemplate jdbcTemplate;

    public DepositDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Deposit> findDepositsForUser(int userId) {
        String sql = "SELECT * FROM deposits WHERE fk_user_id = ? ORDER BY start_date DESC";
        return jdbcTemplate.query(sql, new DepositMapper(), userId);
    }

    public Deposit findDepositById(int depositId) {
        String sql = "SELECT * FROM deposits WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new DepositMapper(), depositId);
    }

    @Override
    public int saveDepositAndReturnDepositId(Deposit deposit) {

        String sqlInsert = "INSERT INTO deposits (time_in_minutes,deposit_amount,fk_user_id) values (?,?,?)";

        jdbcTemplate.update(sqlInsert, deposit.getDepositTime(), deposit.getDepositAmount(), deposit.getUserId());
        String sqlSelect = "SELECT id FROM deposits WHERE id = last_insert_id()";
        int depositId = jdbcTemplate.queryForObject(sqlSelect, Integer.class);

        return depositId;
    }

    @Override
    public void changeDepositStatus(int depositId, DepositStatus depositStatus) {
        String status = depositStatus.name().toLowerCase();
        String sql = "UPDATE deposits set status = ? where id =? ";
        jdbcTemplate.update(sql, status, depositId);
    }

    @Override
    public void changeMoneyWithdraw(int depositId, BigDecimal moneyWithdraw) {
        String sql = "UPDATE deposits set money_to_withdraw = ? where id =? ";
        jdbcTemplate.update(sql, moneyWithdraw, depositId);
    }
}
