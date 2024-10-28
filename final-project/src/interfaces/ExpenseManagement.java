package interfaces;

import entities.ExpenseCategory;
import entities.Expense;
import entities.User;
import exceptions.ExpenseNotFoundException;
import exceptions.InvalidExpenseAmountException;

public interface ExpenseManagement {
    void addExpense(User user, Expense expense);

    void removeExpense(User user, Expense expense) throws ExpenseNotFoundException;

    void updateExpense(
            User user,
            Expense expense,
            Double newAmount,
            String newDate,
            ExpenseCategory newExpenseCategory,
            String newDescription) throws ExpenseNotFoundException, InvalidExpenseAmountException;
}