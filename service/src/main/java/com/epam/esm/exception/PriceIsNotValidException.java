package com.epam.esm.exception;

/**
 * Thrown to indicate that price is not valid.
 */
public class PriceIsNotValidException extends ServiceException {
    /**
     * Constructs a new exception with the specified value.
     *
     * @param value the value
     */
    public PriceIsNotValidException(String value) {
        super(value);
    }

    /**
     * Constructs a new exception with the specified detail message and value.
     *
     * @param message the message
     * @param value   the value
     */
    public PriceIsNotValidException(String message, String value) {
        super(message, value);
    }

    /**
     * Constructs a new exception with the specified detail message, cause and value.
     *
     * @param message the message
     * @param cause   the cause
     * @param value   the value
     */
    public PriceIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    /**
     * Constructs a new exception with the specified cause and value.
     *
     * @param cause the cause
     * @param value the value
     */
    public PriceIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, value,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     * @param value              the value
     */
    public PriceIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
