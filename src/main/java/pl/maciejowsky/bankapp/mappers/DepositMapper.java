package pl.maciejowsky.bankapp.mappers;


import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.Deposit;
import pl.maciejowsky.bankapp.model.enums.DepositStatus;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class DepositMapper implements RowMapper<Deposit> {

    @Override
    public Deposit mapRow(ResultSet rs, int i) throws SQLException {
        DepositStatus depositStatus = DepositStatus.valueOf(rs.getString("status").toUpperCase());

        return new Deposit(rs.getInt("id"),
                rs.getDouble("interest_rate"),
                rs.getInt("time_in_minutes"),
                rs.getBigDecimal("deposit_amount"),
                DateFormatter.timestampToString(rs.getTimestamp("start_date")),
                rs.getInt("fk_user_id"),
                depositStatus,
                rs.getBigDecimal("money_to_withdraw")
        );
    }
}
