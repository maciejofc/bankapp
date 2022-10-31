package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Contact(rs.getString("name"),
                rs.getString("account_number"),
                rs.getInt("fk_user_contact_owner_id"));
    }
}
