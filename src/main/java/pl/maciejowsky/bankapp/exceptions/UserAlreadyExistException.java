package pl.maciejowsky.bankapp.exceptions;

public class UserAlreadyExistException  extends Exception{
    public UserAlreadyExistException(String message) {
        super("Email already exists");
    }
}