package com.epam.esm.exception;

public class NoTagException extends DAOException {
    public NoTagException(String name, int errorCode) {
        super(name, errorCode);
    }

    public NoTagException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    public NoTagException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    public NoTagException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
    }

    public NoTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
