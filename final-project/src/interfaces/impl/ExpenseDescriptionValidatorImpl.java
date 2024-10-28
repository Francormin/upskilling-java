package interfaces.impl;

import exceptions.InvalidExpenseDescriptionException;
import interfaces.ExpenseDescriptionValidator;

public class ExpenseDescriptionValidatorImpl implements ExpenseDescriptionValidator {
    @Override
    public void validateDescription(String description) throws InvalidExpenseDescriptionException {
        if (!description.matches("^[a-zA-Z\\s]*$")) {
            throw new InvalidExpenseDescriptionException("La descripción solo puede contener letras.");
        } else if (description.length() < 5) {
            throw new InvalidExpenseDescriptionException("La descripción debe tener un mínimo de 5 caracteres.");
        }
    }
}