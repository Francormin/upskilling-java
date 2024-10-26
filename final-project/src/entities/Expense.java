package entities;

import exceptions.InvalidExpenseAmountException;

import java.util.Optional;

public class Expense {
    private static Integer counter = 0;
    private Integer id;
    private Double amount;
    private String date;
    private ExpenseCategory expenseCategory;
    private String description;

    public Expense() {
    }

    public Expense(Double amount, String date, ExpenseCategory expenseCategory) throws InvalidExpenseAmountException {
        if (amount <= 0) {
            throw new InvalidExpenseAmountException("The expense amount must be greater than zero.");
        }
        this.id = ++counter;
        this.amount = amount;
        this.date = date;
        this.expenseCategory = expenseCategory;
    }

    public Expense(Double amount, String date, ExpenseCategory expenseCategory, String description) throws InvalidExpenseAmountException {
        this(amount, date, expenseCategory);
        this.description = description.toLowerCase();
    }

    public void updateDetails(
            Optional<Double> newAmount,
            Optional<String> newDate,
            Optional<ExpenseCategory> newCategory,
            Optional<String> newDescription) throws InvalidExpenseAmountException {

        if (newAmount.isPresent()) {
            Double amount = newAmount.get();
            if (amount <= 0) {
                throw new InvalidExpenseAmountException("The expense amount must be greater than zero.");
            }
            this.amount = amount;
        }

        newCategory.ifPresent(expenseCategory -> this.expenseCategory = expenseCategory);
        newDescription.ifPresent(description -> this.description = description.toLowerCase());
        newDate.ifPresent(date -> this.date = date);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ExpenseCategory getCategory() {
        return expenseCategory;
    }

    public void setCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", category='" + expenseCategory.getName() + '\'' +
                ", description='" + description + '\'' +
                " }";
    }
}