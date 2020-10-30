package com.epam.esm.exception;

public class DuplicateTagException extends DAOException {
    public DuplicateTagException(String name, int errorCode) {
        super(name, errorCode);
    }

    public DuplicateTagException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    public DuplicateTagException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    public DuplicateTagException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
    }

    public DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
