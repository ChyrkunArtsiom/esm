package com.epam.esm.exception;

public class NoTemplateException extends RuntimeException {
    public NoTemplateException() {
    }

    public NoTemplateException(String message) {
        super(message);
    }

    public NoTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTemplateException(Throwable cause) {
        super(cause);
    }

    public NoTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
