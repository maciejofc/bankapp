package pl.maciejowsky.bankapp.dao;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.maciejowsky.bankapp.mappers.UserMapper;
import pl.maciejowsky.bankapp.model.FormRegisterUser;
import pl.maciejowsky.bankapp.model.User;
import pl.maciejowsky.bankapp.utils.AccountNumberGenerator;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Component
public class UserDAOImpl implements UserDAO {
    private final static int RECORDS_PER_PAGE = 10;

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByEmail(String email) {

        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new UserMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public User findUserById(int id) {
        User user;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void saveUser(FormRegisterUser user) {
        Date birthDay = null;
        if (user.getBirthDay() != null) {
            birthDay = Date.valueOf(user.getBirthDay());

        }
        Timestamp createdAt = Timestamp.from(Instant.now());
        String userRole = "user";
        String userAuthority = "account_enabled";
        String userType = user.getUserType().name().toLowerCase();
        String sql = "INSERT INTO  users(full_name, birth_day,email,password,user_type,created_at,authorities,roles)  " +
                " VALUES (?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql,
                user.getFullName(),
                birthDay,
                user.getEmail(),
                user.getPassword(),
                userType,
                createdAt,
                userAuthority,
                userRole);
    }


    @Override
    public int findNumberOfUsers() {
        String sql = "SELECT COUNT(*) FROM users WHERE roles = 'user' OR roles='manager' ";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    @Override
    public List<User> findAllUsers(int page) {
        int offsetValue = (page - 1) * RECORDS_PER_PAGE;
        String sql = "SELECT * FROM users WHERE roles ='user' OR roles='manager' LIMIT " + RECORDS_PER_PAGE + " OFFSET " + offsetValue;
        List<User> users = jdbcTemplate.query(
                sql, new UserMapper()
        );
        return users;
    }

    @Override
    public List<User> findAllUsersAndSortByName(int page) {
        int offsetValue = (page - 1) * RECORDS_PER_PAGE;
        String sql = "SELECT * FROM users WHERE roles ='user' OR roles='manager' ORDER BY full_name LIMIT " + RECORDS_PER_PAGE + " OFFSET " + offsetValue;
        List<User> users = jdbcTemplate.query(
                sql, new UserMapper()
        );
        return users;
    }

    @Override
    public List<User> findAllUsersAndSortByAge(int page) {
        int offsetValue = (page - 1) * RECORDS_PER_PAGE;
        String sql = "SELECT * FROM users WHERE roles ='user' OR roles='manager' ORDER BY birth_day LIMIT " + RECORDS_PER_PAGE + " OFFSET " + offsetValue;
        List<User> users = jdbcTemplate.query(
                sql, new UserMapper()
        );
        return users;
    }

    @Override
    public void banUser(int userId) {
        String sql = "UPDATE users" + " SET is_locked =true WHERE id = ?";
        jdbcTemplate.update(sql, userId);

    }

    @Override
    public void unBanUser(int userId) {
        String sql = "UPDATE users" + " SET is_locked =false WHERE id = ?";
        jdbcTemplate.update(sql, userId);

    }


}
