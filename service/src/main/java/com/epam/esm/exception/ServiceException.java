package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a condition that was caught on service layer.
 */
public class ServiceException extends RuntimeException {

    /** Invalid value. */
    private String value;

    /**
     * Constructs a new exception with the specified value.
     *
     * @param value the value
     */
    public ServiceException(String value) {
        this.value = value;
    }

    /**
     * Constructs a new exception with the specified detail message and value.
     *
     * @param message the message
     * @param value   the value
     */
    public ServiceException(String message, String value) {
        super(message);
        this.value = value;
    }

    /**
     * Constructs a new exception with the specified detail message, cause and value.
     *
     * @param message the message
     * @param cause   the cause
     * @param value   the value
     */
    public ServiceException(String message, Throwable cause, String value) {
        super(message, cause);
        this.value = value;
    }

    /**
     * Constructs a new exception with the specified cause and value.
     *
     * @param cause the cause
     * @param value the value
     */
    public ServiceException(Throwable cause, String value) {
        super(cause);
        this.value = value;
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
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
