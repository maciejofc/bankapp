package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.enums.AnnouncementVisibility;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class BankAnnouncementMapper implements RowMapper<BankAnnouncement> {
    @Override
    public BankAnnouncement mapRow(ResultSet rs, int rowNum) throws SQLException {

        String announcementVisibilityString = rs.getString("visibility");
        AnnouncementVisibility announcementVisibility = AnnouncementVisibility.valueOf(announcementVisibilityString.toUpperCase());

        return new BankAnnouncement(
                rs.getInt("id"),
                rs.getString("content"),
                announcementVisibility,
                DateFormatter.timestampToString(rs.getTimestamp("created_at"))
        );
    }
}
