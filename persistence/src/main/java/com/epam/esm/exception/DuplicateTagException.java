package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a {@link com.epam.esm.entity.Tag} duplicate object.
 */
public class DuplicateTagException extends DAOException {
    /**
     * Constructs a new exception with the specified name.
     *
     * @param name      the name
     */
    public DuplicateTagException(String name) {
        super(name);
    }

    /**
     * Constructs a new exception with the specified detail message, name.
     *
     * @param message   the message
     * @param name      the name
     */
    public DuplicateTagException(String message, String name) {
        super(message, name);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name.
     *
     * @param message   the message
     * @param cause     the cause
     * @param name      the name
     */
    public DuplicateTagException(String message, Throwable cause, String name) {
        super(message, cause, name);
    }

    /**
     * Constructs a new exception with the specified cause, name.
     *
     * @param cause     the cause
     * @param name      the name
     */
    public DuplicateTagException(Throwable cause, String name) {
        super(cause, name);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     * @param name               the name
     */
    public DuplicateTagException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace,
                                 String name) {
        super(message, cause, enableSuppression, writableStackTrace, name);
    }
}
