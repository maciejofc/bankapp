package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.RequestDAO;
import pl.maciejowsky.bankapp.exceptions.RequestException;
import pl.maciejowsky.bankapp.model.Request;
import pl.maciejowsky.bankapp.model.enums.RequestStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestDAO requestDAO;



    @Override
    public List<Request> getAllPendingRequests() {
        return requestDAO.findAllRequests().stream()
                .filter(request -> request.getRequestStatus() == RequestStatus.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> getUserRequestsByUserId(int userId) {
        return requestDAO.findAllRequests().stream()
                .filter(request -> request.getUserIdOfRequest() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public void sendRequest(Request request, int userIdOfRequest) throws RequestException {
        request.setUserIdOfRequest(userIdOfRequest);
        Optional<Request> optionalRequest = getUserRequestsByUserId(userIdOfRequest).stream()
                .filter(rq -> rq.getRequestStatus() == RequestStatus.PENDING)
                .findFirst();
        if (optionalRequest.isEmpty()) {
            requestDAO.saveRequest(request);
        } else
            throw new RequestException("You need to wait for response of previous sended funds request");


    }


    @Override
    public void changeRequestStatus(int requestId, int managerAccepterId, RequestStatus requestStatus) throws RequestException {
        Request request = requestDAO.findRequestById(requestId);
        //checking if other manager made decision before we click
        if (request.getRequestStatus() == RequestStatus.PENDING) {
            requestDAO.updateRequestStatus(requestId, managerAccepterId, requestStatus);
        } else
            throw new RequestException("Ops, someone was faster than you and have already responded to request");


    }


}
