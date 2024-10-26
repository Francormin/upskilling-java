package entities;

import interfaces.ExpenseManagement;
import exceptions.ExpenseNotFoundException;
import exceptions.InvalidExpenseAmountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User implements ExpenseManagement {
    private static Integer counter = 0;
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Expense> expenses;

    public User() {
    }

    public User(String name, String surname) {
        this.id = ++counter;
        this.name = name;
        this.surname = surname;
        this.expenses = new ArrayList<>();
    }

    public User(String name, String surname, String email, String password) {
        this(name, surname);
        this.email = email;
        this.password = password;
    }

    // ExpenseManagement interface implementation
    @Override
    public void addExpense(Expense expense) {
        expenses.add(expense);
        System.out.println("Expense added: " + expense.getDescription());
    }

    @Override
    public void removeExpense(Expense expense) throws ExpenseNotFoundException {
        if (!expenses.contains(expense)) {
            throw new ExpenseNotFoundException("Expense not found.");
        }
        expenses.remove(expense);
        System.out.println("Expense with ID " + expense.getId() + " removed.");
    }

    @Override
    public void updateExpense(
            Expense expense,
            Double newAmount,
            String newDate,
            ExpenseCategory newExpenseCategory,
            String newDescription) throws ExpenseNotFoundException, InvalidExpenseAmountException {

        if (!expenses.contains(expense)) {
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

    // Validation method
    public Expense getExpenseAtIndex(int index) throws ExpenseNotFoundException {
        if (index < 0 || index >= expenses.size()) {
            throw new ExpenseNotFoundException("Expense not found at index: " + index);
        }
        return expenses.get(index);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", expenses=" + expenses +
                " }";
    }
}