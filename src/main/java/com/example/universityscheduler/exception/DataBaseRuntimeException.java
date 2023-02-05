package com.example.universityscheduler.exception;

public class DataBaseRuntimeException extends RuntimeException {

    public DataBaseRuntimeException() {

    }

    public DataBaseRuntimeException(String message) {
        super(message);
    }

    public DataBaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseRuntimeException(Throwable cause) {
        super(cause);
    }

}
