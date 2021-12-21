package com.jadefinger.utils.exception;

public class FileContextException extends Exception{
    public FileContextException() {
    }

    public FileContextException(String message) {
        super(message);
    }

    public FileContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileContextException(Throwable cause) {
        super(cause);
    }

    public FileContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
