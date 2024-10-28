package entities;

import exceptions.ExpenseNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class User {
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