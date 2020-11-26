package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a condition that was caught on DAO layer.
 */
public class DAOException extends RuntimeException {

    /** Parameter which caused an exception. */
    private String name;

    /**
     * Constructs a new exception with the specified name.
     *
     * @param name      the name
     */
    public DAOException(String name) {
        this.name = name;
    }

    /**
     * Constructs a new exception with the specified detail message, name.
     *
     * @param message   the message
     * @param name      the name
     */
    public DAOException(String message, String name) {
        super(message);
        this.name = name;
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name.
     *
     * @param message   the message
     * @param cause     the cause
     * @param name      the name
     */
    public DAOException(String message, Throwable cause, String name) {
        super(message, cause);
        this.name = name;
    }

    /**
     * Constructs a new exception with the specified cause, name.
     *
     * @param cause     the cause
     * @param name      the name
     */
    public DAOException(Throwable cause, String name) {
        super(cause);
        this.name = name;
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
    public DAOException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace,
                        String name) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
