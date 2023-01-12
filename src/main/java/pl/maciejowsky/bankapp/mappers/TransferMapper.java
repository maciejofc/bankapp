package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.Transfer;
import pl.maciejowsky.bankapp.model.enums.TransferType;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class TransferMapper implements RowMapper<Transfer> {

    @Override
    public Transfer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Transfer(
                rs.getInt("id"),
                rs.getString("from_account"),
                rs.getString("to_account"),
                rs.getBigDecimal("amount"),
                TransferType.valueOf(rs.getString("transfer_type").toUpperCase()),
                DateFormatter.timestampToString(rs.getTimestamp("date_of_execution")),
                DateFormatter.timestampToString(rs.getTimestamp("date_of_receiving"))
        );
    }
}
