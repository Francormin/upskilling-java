package interfaces;

import entities.ExpenseCategory;
import entities.Expense;
import exceptions.ExpenseNotFoundException;
import exceptions.InvalidExpenseAmountException;

public interface ExpenseManagement {
    void addExpense(Expense expense);
    void removeExpense(Expense expense) throws ExpenseNotFoundException;
    void updateExpense(
            Expense expense,
            Double newAmount,
            String newDate,
            ExpenseCategory newExpenseCategory,
            String newDescription
    ) throws ExpenseNotFoundException, InvalidExpenseAmountException;
}