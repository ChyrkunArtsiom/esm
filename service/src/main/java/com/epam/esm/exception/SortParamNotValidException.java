package com.epam.esm.exception;

public class SortParamNotValidException extends ServiceException {
    public SortParamNotValidException(String value) {
        super(value);
    }

    public SortParamNotValidException(String message, String value) {
        super(message, value);
    }

    public SortParamNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public SortParamNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public SortParamNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
