package com.epam.esm.exception;

public class ServiceException extends RuntimeException {
    private String value;

    public ServiceException(String value) {
        this.value = value;
    }

    public ServiceException(String message, String value) {
        super(message);
        this.value = value;
    }

    public ServiceException(String message, Throwable cause, String value) {
        super(message, cause);
        this.value = value;
    }

    public ServiceException(Throwable cause, String value) {
        super(cause);
        this.value = value;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
