package com.javaupskilling.finalspringproject.model;

public class Expense {

    private Long id;
    private Double amount;
    private String date;
    private ExpenseCategory expenseCategory;
    private String description;

    public Expense() {
    }

    public Expense(Double amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public Expense(Double amount, String date, ExpenseCategory expenseCategory, String description) {
        this(amount, date);
        this.expenseCategory = expenseCategory;
        this.description = description.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
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
        return "Expense{" +
            "id=" + id +
            ", amount=" + amount +
            ", date='" + date + '\'' +
            ", expenseCategory=" + expenseCategory +
            ", description='" + description + '\'' +
            '}';
    }

}
