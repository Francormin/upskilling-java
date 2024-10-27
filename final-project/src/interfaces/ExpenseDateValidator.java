package interfaces;

import exceptions.InvalidExpenseDateException;

@FunctionalInterface
public interface ExpenseDateValidator {
    void validateDate(String date) throws InvalidExpenseDateException;
}