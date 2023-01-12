package pl.maciejowsky.bankapp.exceptions;

public class SamePasswordException extends Exception {
    public SamePasswordException(String message) {
        super(message);
    }
}
