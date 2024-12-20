package interfaces.impl;

import exceptions.InvalidExpenseAmountException;
import interfaces.ExpenseAmountValidator;

public class ExpenseAmountValidatorImpl implements ExpenseAmountValidator {
    @Override
    public void validateAmount(double amount) throws InvalidExpenseAmountException {
        if (amount <= 0) {
            throw new InvalidExpenseAmountException("El monto del gasto debe ser mayor a cero.");
        }
    }
}