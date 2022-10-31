package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.Transfer;
import pl.maciejowsky.bankapp.model.UserAccount;
import pl.maciejowsky.bankapp.model.enums.TransferType;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountMapper implements RowMapper<UserAccount> {
    @Override
    public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserAccount(
                rs.getInt("id"),
                rs.getString("accountNumber"),
                rs.getBigDecimal("balance"),
                rs.getInt("fk_user_id")
        );
    }
}
