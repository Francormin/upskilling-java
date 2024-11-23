package exceptions;

public class InvalidExpenseDateException extends Exception {
    public InvalidExpenseDateException(String message) {
        super(message);
    }
}