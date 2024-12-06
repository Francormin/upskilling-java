package com.javaupskilling.finalspringproject.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s with ID %d not found", entityName, id));
    }

    public EntityNotFoundException(String entityName, String message) {
        super(String.format("%s: %s", entityName, message));
    }

}
