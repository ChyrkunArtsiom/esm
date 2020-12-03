package com.epam.esm.exception;

import com.epam.esm.handler.exceptiontemplate.ExceptionType;

/**
 * Thrown to indicate that there is no {@link ExceptionType} object for specific exception.
 */
public class NoTemplateException extends RuntimeException {
    /**
     * Constructs a new exception.
     */
    public NoTemplateException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message   the message
     */
    public NoTemplateException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message, cause.
     *
     * @param message  the message
     * @param cause    the cause
     */
    public NoTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause    the cause
     */
    public NoTemplateException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public NoTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
