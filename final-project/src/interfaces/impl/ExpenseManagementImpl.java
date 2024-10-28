package interfaces.impl;

import entities.Expense;
import entities.ExpenseCategory;
import entities.User;
import interfaces.ExpenseManagement;
import exceptions.ExpenseNotFoundException;
import exceptions.InvalidExpenseAmountException;

import java.util.Optional;

public class ExpenseManagementImpl implements ExpenseManagement {
    @Override
    public void addExpense(User user, Expense expense) {
        user.getExpenses().add(expense);
        System.out.println("Expense added: " + expense.getDescription());
    }

    @Override
    public void removeExpense(User user, Expense expense) throws ExpenseNotFoundException {
        if (!user.getExpenses().contains(expense)) {
            throw new ExpenseNotFoundException("Expense not found.");
        }
        user.getExpenses().remove(expense);
        System.out.println("Expense with ID " + expense.getId() + " removed.");
    }

    @Override
    public void updateExpense(
            User user,
            Expense expense,
            Double newAmount,
            String newDate,
            ExpenseCategory newExpenseCategory,
            String newDescription) throws ExpenseNotFoundException, InvalidExpenseAmountException {

        if (!user.getExpenses().contains(expense)) {
            throw new ExpenseNotFoundException("Expense not found.");
        }
        expense.updateDetails(
                Optional.ofNullable(newAmount),
                Optional.ofNullable(newDate),
                Optional.ofNullable(newExpenseCategory),
                Optional.ofNullable(newDescription)
        );
        System.out.println("Expense with ID " + expense.getId() + " updated.");

    }
}