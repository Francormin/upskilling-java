package exceptions;

public class InvalidExpenseDescriptionException extends Exception {
    public InvalidExpenseDescriptionException(String message) {
        super(message);
    }
}