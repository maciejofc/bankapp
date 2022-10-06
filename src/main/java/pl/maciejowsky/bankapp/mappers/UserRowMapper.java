package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.enums.UserType;
import pl.maciejowsky.bankapp.model.User;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        String createdAt = null;
        if (!(rs.getTimestamp("created_at") == null)) {
            createdAt = DateFormatter.timestampToString(rs.getTimestamp("created_at"));

        }
        String updatedAt = null;
        if (!(rs.getTimestamp("updated_at") == null)) {
            updatedAt = DateFormatter.timestampToString(rs.getTimestamp("updated_at"));

        }

        LocalDate birthDay = rs.getDate("birth_day").toLocalDate();
        UserType userType = null;
        if (rs.getString("user_type") != null) {

            userType = UserType.valueOf(rs.getString("user_type").toUpperCase());
        }

        return new User(
                rs.getInt("id"),
                rs.getString("full_name"),
                birthDay,
                rs.getString("email"),
                rs.getString("password"),
                userType,
                createdAt,
                updatedAt,
                rs.getString("authorities"),
                rs.getString("roles"),
                rs.getBoolean("is_locked")
        );


    }
}
