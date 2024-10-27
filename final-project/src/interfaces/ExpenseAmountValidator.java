package interfaces;

import exceptions.InvalidExpenseAmountException;

@FunctionalInterface
public interface ExpenseAmountValidator {
    void validateAmount(double amount) throws InvalidExpenseAmountException;
}