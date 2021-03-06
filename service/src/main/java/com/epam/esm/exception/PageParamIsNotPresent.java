package com.epam.esm.exception;

/**
 * Thrown to indicate that one of parameters (page or size) is not present while other is presented during get method.
 */
public class PageParamIsNotPresent extends RuntimeException {
    /**
     * Constructs a new exception.
     */
    public PageParamIsNotPresent() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message   the message
     */
    public PageParamIsNotPresent(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message, cause.
     *
     * @param message  the message
     * @param cause    the cause
     */
    public PageParamIsNotPresent(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause    the cause
     */
    public PageParamIsNotPresent(Throwable cause) {
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
    public PageParamIsNotPresent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
