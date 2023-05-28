package com.example.universityscheduler.exception;

public class InternalException extends RuntimeException {

    public InternalException() {

    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }
}
