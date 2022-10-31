package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.Transfer;
import pl.maciejowsky.bankapp.model.enums.AnnouncementVisibility;
import pl.maciejowsky.bankapp.model.enums.TransferType;
import pl.maciejowsky.bankapp.model.enums.UserType;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferMapper implements RowMapper<Transfer> {

    @Override
    public Transfer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Transfer(
                rs.getInt("id"),
                rs.getString("from_account"),
                rs.getString("to_account"),
                rs.getBigDecimal("amount"),
                TransferType.valueOf(rs.getString("transfer_type").toUpperCase()),
                DateFormatter.timestampToString(rs.getTimestamp("sent_at")),
                DateFormatter.timestampToString(rs.getTimestamp("receive_at"))
        );
    }
}
