package com.demo.backend.exception;

public class CustomValidationException extends IllegalArgumentException {

    public CustomValidationException(String message) {
        super(message);
    }
}
