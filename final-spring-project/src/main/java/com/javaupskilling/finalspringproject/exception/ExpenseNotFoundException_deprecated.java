package com.javaupskilling.finalspringproject.exception;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

}
