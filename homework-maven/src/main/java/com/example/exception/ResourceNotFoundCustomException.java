package com.example.exception;

public class ResourceNotFoundCustomException extends RuntimeException {

    public ResourceNotFoundCustomException(String errorMessage) {
        super(errorMessage);
    }

}
