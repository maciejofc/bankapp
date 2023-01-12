package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.exceptions.RequestException;
import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;

import java.util.List;

public interface RequestService {


    List<Request> getAllPendingRequests();

    List<Request> getUserRequestsByUserId(int userId);


    void sendRequest(Request request, int userIdOfRequest) throws RequestException;

    void changeRequestStatus(int requestId, int managerAccepterId, RequestStatus requestStatus) throws RequestException;


}
