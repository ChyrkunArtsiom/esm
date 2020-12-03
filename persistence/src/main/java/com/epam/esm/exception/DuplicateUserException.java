package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a {@link com.epam.esm.entity.User} duplicate object.
 */
public class DuplicateUserException extends DAOException {
    /**
     * Constructs a new exception with the specified name.
     *
     * @param name      the name
     */
    public DuplicateUserException(String name) {
        super(name);
    }

    /**
     * Constructs a new exception with the specified detail message, name and code.
     *
     * @param message   the message
     * @param name      the name
     */
    public DuplicateUserException(String message, String name) {
        super(message, name);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name.
     *
     * @param message   the message
     * @param cause     the cause
     * @param name      the name
     */
    public DuplicateUserException(String message, Throwable cause, String name) {
        super(message, cause, name);
    }

    /**
     * Constructs a new exception with the specified cause, name.
     *
     * @param cause     the cause
     * @param name      the name
     */
    public DuplicateUserException(Throwable cause, String name) {
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
    public DuplicateUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name) {
        super(message, cause, enableSuppression, writableStackTrace, name);
    }
}
