import entities.Expense;
import entities.ExpenseCategory;
import entities.User;
import exceptions.ExpenseNotFoundException;
import exceptions.InvalidExpenseAmountException;
import interfaces.ExpenseManagement;
import interfaces.impl.ExpenseManagementImpl;

public class ExpenseTrackerApp {
    public static void main(String[] args) throws InvalidExpenseAmountException {
        // Creating a new User
        User user = new User("John", "Doe");

        // Creating new Expense Categories
        ExpenseCategory foodExpenseCategory = new ExpenseCategory("Food", "Groceries and dining out");
        ExpenseCategory travelExpenseCategory = new ExpenseCategory("Travel", "Transportation");

        // ExpenseManagement interface implementation
        ExpenseManagement expenseManagement = new ExpenseManagementImpl();

        // Creating new Expenses and adding it to the User created earlier
        try {
            Expense expense1 = new Expense(25.5, "20/10/2023", foodExpenseCategory, "Dinner at restaurant");
            Expense expense2 = new Expense(14.9, "23/10/2023", foodExpenseCategory, "Groceries");

            expenseManagement.addExpense(user, expense1);
            expenseManagement.addExpense(user, expense2);
        } catch (InvalidExpenseAmountException e) {
            System.err.println("Adding expense error: " + e.getMessage());
        }

        System.out.println("\nExpenses of " + user.getName() + " " + user.getSurname() + ":");
        for (Expense expense : user.getExpenses()) {
            System.out.println(expense);
        }

        // Removing an Expense from the User created earlier
        try {
            // Index validation previous to remove
            Expense expenseToRemove = user.getExpenseAtIndex(1);
            expenseManagement.removeExpense(user, expenseToRemove);
        } catch (ExpenseNotFoundException e) {
            System.err.println("Removing expense error: " + e.getMessage());
        }

        System.out.println("\nExpenses of " + user.getName() + " " + user.getSurname() + ":");
        for (Expense expense : user.getExpenses()) {
            System.out.println(expense);
        }

        // Updating an Expense from the User created earlier
        try {
            // Index validation previous to update
            Expense expenseToUpdate = user.getExpenseAtIndex(0);
            expenseManagement.updateExpense(
                    user,
                    expenseToUpdate,
                    null,
                    null,
                    travelExpenseCategory,
                    "Plane and buses"
            );
        } catch (ExpenseNotFoundException e) {
            System.err.println("Updating expense error: " + e.getMessage());
        }

        System.out.println("\nExpenses of " + user.getName() + " " + user.getSurname() + ":");
        for (Expense expense : user.getExpenses()) {
            System.out.println(expense);
        }
    }
}