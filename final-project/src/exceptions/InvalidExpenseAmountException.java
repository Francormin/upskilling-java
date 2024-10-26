package exceptions;

public class InvalidExpenseAmountException extends Exception {
    public InvalidExpenseAmountException(String message) {
        super(message);
    }
}