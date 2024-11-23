package interfaces;

import exceptions.InvalidExpenseDescriptionException;

@FunctionalInterface
public interface ExpenseDescriptionValidator {
    void validateDescription(String description) throws InvalidExpenseDescriptionException;
}