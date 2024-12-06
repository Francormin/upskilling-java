package com.javaupskilling.finalspringproject.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtil {

    public static void validateId(Long id) {
        log.info("validateId - Input received from params: {}", id);
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
    }

    public static void validateDate(String date) {
        log.info("validateDate - Input received from params: {}", date);
        if (date.isBlank()) {
            throw new IllegalArgumentException("Date must not be blank");
        }
    }

}
