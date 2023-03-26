package com.example.universityscheduler.exception;

public class ConfilctException extends RuntimeException {

    public ConfilctException() {

    }

    public ConfilctException(String message) {
        super(message);
    }

    public ConfilctException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfilctException(Throwable cause) {
        super(cause);
    }
}
