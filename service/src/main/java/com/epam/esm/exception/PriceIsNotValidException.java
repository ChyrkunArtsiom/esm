package com.epam.esm.exception;

public class PriceIsNotValidException extends ServiceException {
    public PriceIsNotValidException(String value) {
        super(value);
    }

    public PriceIsNotValidException(String message, String value) {
        super(message, value);
    }

    public PriceIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public PriceIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public PriceIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
