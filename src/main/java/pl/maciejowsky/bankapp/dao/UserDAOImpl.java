package pl.maciejowsky.bankapp.dao;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import pl.maciejowsky.bankapp.mappers.UserMapper;
import pl.maciejowsky.bankapp.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Component
public class UserDAOImpl implements UserDAO {
    private final static int RECORDS_PER_PAGE = 10;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertIntoUser;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
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
    public int saveUser(User user) {
        Date birthDay = null;
        if (user.getBirthDay() != null) {
            birthDay = Date.valueOf(user.getBirthDay());

        }
        Timestamp createdAt = Timestamp.from(Instant.now());
        String userRole = user.getRoles();
        String userAuthority = user.getAuthorities();
        String userType;
        if (user.getUserType() == null) {
            userType = null;
        } else {
            userType = user.getUserType().name().toLowerCase();
        }
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("full_name", user.getFullName())
                .addValue("birth_day", birthDay)
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("user_type", userType)
                .addValue("created_at", createdAt)
                .addValue("authorities", userAuthority)
                .addValue("roles", userRole);

        Number newId = insertIntoUser.executeAndReturnKey(params);


//
//        String sql = "INSERT INTO  users(full_name, birth_day,email,password,user_type,created_at,authorities,roles)  " +
//                " VALUES (?,?,?,?,?,?,?,?);";
//        jdbcTemplate.update(sql,
//                user.getFullName(),
//                birthDay,
//                user.getEmail(),
//                user.getPassword(),
//                userType,
//                createdAt,
//                userAuthority,
//                userRole);
        return newId.intValue();
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

    @Override
    public void updateEmail(int userId, String newEmail) {
        String sql = "UPDATE users" + " SET email = ? AND updated_at =? WHERE id = ?";
        jdbcTemplate.update(sql, newEmail, Instant.now(), userId);

    }

    @Override
    public void updatePassword(int userId, String newPassword) {

    }
}
