package pl.maciejowsky.bankapp.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.RequestMapper;
import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;

import java.util.List;

@Component
public class RequestDAOImpl implements RequestDAO {
    private final JdbcTemplate jdbcTemplate;

    public RequestDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Request> findAllRequests() {
        String sql = "SELECT * FROM funds_requests ORDER BY FIELD(status,'pending','accept','decline') LIMIT 10";
        return jdbcTemplate.query(sql, new RequestMapper());

    }

    @Override
    public Request findRequestById(int requestId) {
        String sql = "SELECT * FROM funds_requests WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new RequestMapper(), requestId);
    }


    @Override
    public void saveRequest(Request request) {
        String sql = "INSERT INTO funds_requests(amount,fk_user_id_of_request) VALUES(?,?)";

        jdbcTemplate.update(sql,
                request.getAmount(),
                request.getUserIdOfRequest()
        );
    }

    @Override
    public void updateRequestStatus(int requestId, int manager_id_accepter, RequestStatus requestStatus) {
        String status = requestStatus.name().toLowerCase();
        String sql = "UPDATE funds_requests SET status = ?, fk_manager_id_accepter=? WHERE id =? ";
        jdbcTemplate.update(sql, status, manager_id_accepter, requestId);
    }
}
