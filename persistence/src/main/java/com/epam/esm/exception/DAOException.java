package com.epam.esm.exception;

public class DAOException extends RuntimeException {

    private ExceptionType type;

    public DAOException(ExceptionType type) {
        this.type = type;
    }

    public DAOException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public DAOException(String message, Throwable cause, ExceptionType type) {
        super(message, cause);
        this.type = type;
    }

    public DAOException(Throwable cause, ExceptionType type) {
        super(cause);
        this.type = type;
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionType type) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.type = type;
    }
}
