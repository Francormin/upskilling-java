package com.example.homeworkspringdatajpa.util;

public class ValidationUtil {

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
    }

}
