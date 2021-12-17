package com.jadefinger.utils.exception;

public class InvalidFileNameException extends RuntimeException {

    public InvalidFileNameException() {
    }

    public InvalidFileNameException(String message) {
        super(message);
    }

    public InvalidFileNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFileNameException(Throwable cause) {
        super(cause);
    }

    public InvalidFileNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
