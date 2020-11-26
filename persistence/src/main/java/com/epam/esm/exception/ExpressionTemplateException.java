package com.epam.esm.exception;

/**
 * Thrown to indicate that {@link com.epam.esm.sort.ExpressionTemplate} object cannot be created.
 */
public class ExpressionTemplateException extends RuntimeException {
    /**
     * Constructs a new exception.
     */
    public ExpressionTemplateException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message   the message
     */
    public ExpressionTemplateException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message, cause.
     *
     * @param message   the message
     * @param cause     the cause
     */
    public ExpressionTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause     the cause
     */
    public ExpressionTemplateException(Throwable cause) {
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
    public ExpressionTemplateException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
