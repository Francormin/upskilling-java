package com.javaupskilling.finalspringproject.model;

public class ExpenseCategory {

    private Long id;
    private String name;
    private String description;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name, String description) {
        this.name = name.toLowerCase();
        this.description = description.toLowerCase();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

}
