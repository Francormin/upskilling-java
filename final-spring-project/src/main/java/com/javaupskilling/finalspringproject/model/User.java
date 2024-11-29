package com.javaupskilling.finalspringproject.model;

import java.util.List;

public class User {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private List<Expense> expenses;

    public User() {
    }

    public User(String name, String surname, String email, List<Expense> expenses) {
        this.name = name;
        this.surname = surname;
        this.email = email.toLowerCase();
        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", email='" + email + '\'' +
            ", expenses=" + expenses +
            '}';
    }

}
