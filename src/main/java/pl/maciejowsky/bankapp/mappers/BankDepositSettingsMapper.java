package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BankDepositSettingsMapper implements RowMapper<BankDepositSettings> {

    @Override
    public BankDepositSettings mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new BankDepositSettings(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("min_deposit_time_in_min"),
                rs.getDouble("minute_percentage_rate"),
                rs.getInt("min_amount"),
                UserType.valueOf(rs.getString("intended_for").toUpperCase())
        );
    }
}



