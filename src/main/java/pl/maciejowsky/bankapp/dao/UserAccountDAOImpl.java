package pl.maciejowsky.bankapp.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.UserAccountMapper;
import pl.maciejowsky.bankapp.model.UserAccount;
import pl.maciejowsky.bankapp.utils.AccountNumberGenerator;

import java.math.BigDecimal;

@Component
public class UserAccountDAOImpl implements UserAccountDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserAccountDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalanceOfUserByAccountNumber(String accountNumber) {

        String sql = "SELECT balance FROM users_account WHERE account_number = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, accountNumber);

    }

    public String createNonExistingAccountNumber() {
        while (true) {
            String possibleNewAccountNumber = AccountNumberGenerator.generateNumber();
            if (!checkIfAccountNumberExist(possibleNewAccountNumber)) {
                return possibleNewAccountNumber;
            }
        }

    }

    @Override
    public void saveUserAccount(int userId) {

        String nonExistingAccountNumber = createNonExistingAccountNumber();

        String sql = "INSERT INTO  users_account(account_number, fk_user_id)  " +
                " VALUES (?,?);";
        jdbcTemplate.update(sql,
                nonExistingAccountNumber, userId
        );
    }


    @Override
    public boolean checkIfAccountNumberExist(String accountNumber) {
        String sql = "SELECT EXISTS(SELECT * from users_account WHERE account_number = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, accountNumber);
    }

    @Override
    public void modifyBalanceOnAccount(String accountNumber, BigDecimal moneyToManipulate, AccountBalanceManipulation accountBalanceManipulation) {
        String sql;
        if (accountBalanceManipulation.equals(AccountBalanceManipulation.SUPPLY)) {
            sql = "UPDATE users_account SET balance = balance + ? WHERE account_number = ? ";

        } else {
            sql = "UPDATE users_account SET balance = balance - ? WHERE account_number = ? ";

        }
        jdbcTemplate.update(sql, moneyToManipulate, accountNumber);
    }


    @Override
    public String getAccountNumberByUserId(int userId) {
        String accountNumber;
        try {
            String sql = "SELECT account_number FROM users_account WHERE fk_user_id = ?";
            accountNumber = jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return accountNumber;
    }

    @Override
    public int getUserIdByAccountNumber(String accountNumber) {
        String sql = "SELECT fk_user_id FROM users_account WHERE account_number = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, accountNumber);
    }

    @Override
    public UserAccount getUserAccountById(int userId) {
        UserAccount userAccount;
        try {
            String sql = "SELECT * FROM users_account WHERE fk_user_id = ?";
            userAccount = jdbcTemplate.queryForObject(sql, new UserAccountMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return userAccount;

    }
}
