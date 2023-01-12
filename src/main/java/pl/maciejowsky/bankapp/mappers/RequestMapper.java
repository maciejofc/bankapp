package pl.maciejowsky.bankapp.mappers;

import org.springframework.jdbc.core.RowMapper;
import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;
import pl.maciejowsky.bankapp.model.enums.TransferType;
import pl.maciejowsky.bankapp.utils.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RequestMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Request(
                rs.getInt("id"),
                rs.getInt("amount"),
                rs.getInt("fk_user_id_of_request"),
                rs.getInt("fk_manager_id_accepter"),
                RequestStatus.valueOf(rs.getString("status").toUpperCase())
        );
    }
}
