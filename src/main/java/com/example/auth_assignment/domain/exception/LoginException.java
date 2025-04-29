package com.example.auth_assignment.domain.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}