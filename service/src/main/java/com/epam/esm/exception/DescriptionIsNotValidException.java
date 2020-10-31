package com.epam.esm.exception;

public class DescriptionIsNotValidException extends ServiceException {
    public DescriptionIsNotValidException(String value) {
        super(value);
    }

    public DescriptionIsNotValidException(String message, String value) {
        super(message, value);
    }

    public DescriptionIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public DescriptionIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public DescriptionIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
