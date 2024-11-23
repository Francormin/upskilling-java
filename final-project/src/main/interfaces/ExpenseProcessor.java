package interfaces;

import entities.Expense;

import java.util.List;

@FunctionalInterface
public interface ExpenseProcessor {
    Double process(List<Expense> expenses);
}