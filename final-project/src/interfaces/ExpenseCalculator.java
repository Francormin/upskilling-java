package interfaces;

import entities.Expense;

public interface ExpenseCalculator {
    double calculateExpense(Expense expense);

    double calculateTotalExpenses(Expense[] expenses);
}