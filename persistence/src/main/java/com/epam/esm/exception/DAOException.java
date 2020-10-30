package com.epam.esm.exception;

public class DAOException extends RuntimeException {

    private String name;
    private int errorCode;

    public DAOException(String name, int errorCode) {
        this.name = name;
        this.errorCode = errorCode;
    }

    public DAOException(String message, String name, int errorCode) {
        super(message);
        this.name = name;
        this.errorCode = errorCode;
    }

    public DAOException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause);
        this.name = name;
        this.errorCode = errorCode;
    }

    public DAOException(Throwable cause, String name, int errorCode) {
        super(cause);
        this.name = name;
        this.errorCode = errorCode;
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
        this.errorCode = errorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
