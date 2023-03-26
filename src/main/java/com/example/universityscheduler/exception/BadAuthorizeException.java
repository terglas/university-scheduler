package com.example.universityscheduler.exception;

public class BadAuthorizeException extends RuntimeException {

    public BadAuthorizeException() {

    }

    public BadAuthorizeException(String message) {
        super(message);
    }

    public BadAuthorizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadAuthorizeException(Throwable cause) {
        super(cause);
    }
}
