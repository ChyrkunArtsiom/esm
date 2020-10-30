package com.epam.esm.exception;

public class TagNameIsNotValidException extends ServiceException{
    public TagNameIsNotValidException(String value) {
        super(value);
    }

    public TagNameIsNotValidException(String message, String value) {
        super(message, value);
    }

    public TagNameIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public TagNameIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public TagNameIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
