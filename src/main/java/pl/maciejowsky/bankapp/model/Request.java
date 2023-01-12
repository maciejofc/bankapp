package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.RequestStatus;

public class Request {
    private int id;
    private int amount;
    private int userIdOfRequest;
    private int managerIdAccepter;
    private RequestStatus requestStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserIdOfRequest() {
        return userIdOfRequest;
    }

    public void setUserIdOfRequest(int userIdOfRequest) {
        this.userIdOfRequest = userIdOfRequest;
    }

    public int getManagerIdAccepter() {
        return managerIdAccepter;
    }

    public void setManagerIdAccepter(int managerIdAccepter) {
        this.managerIdAccepter = managerIdAccepter;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Request() {
    }

    public Request(int id, int amount, int userIdOfRequest, int managerIdAccepter, RequestStatus requestStatus) {
        this.id = id;
        this.amount = amount;
        this.userIdOfRequest = userIdOfRequest;
        this.managerIdAccepter = managerIdAccepter;
        this.requestStatus = requestStatus;
    }
}
