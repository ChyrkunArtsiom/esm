package com.epam.esm.exception;

public class DurationIsNotValidException extends ServiceException {
    public DurationIsNotValidException(String value) {
        super(value);
    }

    public DurationIsNotValidException(String message, String value) {
        super(message, value);
    }

    public DurationIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public DurationIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public DurationIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
