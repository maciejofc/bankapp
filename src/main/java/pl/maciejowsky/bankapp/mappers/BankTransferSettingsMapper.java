package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.BankTransferSettings;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransferSettingsMapper implements RowMapper<BankTransferSettings> {
    @Override
    public BankTransferSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BankTransferSettings(
                rs.getInt("id"),
                rs.getInt("transfer_express"),
                rs.getInt("transfer_fast_one_min"),
                rs.getInt("transfer_normal_two_min"),
                rs.getDouble("discount_for_entrepreneur_in_percentage"),
                DateFormatter.timestampToString(rs.getTimestamp("created_at"))
        );
    }
}
