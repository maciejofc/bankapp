package pl.maciejowsky.bankapp.dao;

import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;

import java.util.List;

public interface RequestDAO {

    List<Request> findAllRequests();

    Request findRequestById(int requestId);

    void saveRequest(Request request);

    void updateRequestStatus(int requestId, int manager_id_accepter, RequestStatus requestStatus);


}
