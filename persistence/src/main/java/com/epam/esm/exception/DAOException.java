package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a condition that was caught on DAO layer.
 */
public class DAOException extends RuntimeException {

    /** Parameter which caused an exception. */
    private String name;
    /** Number which indicated a type of cause of an exception. */
    private int errorCode;

    /**
     * Constructs a new exception with the specified name and code.
     *
     * @param name      the name
     * @param errorCode the error code
     */
    public DAOException(String name, int errorCode) {
        this.name = name;
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new exception with the specified detail message, name and code.
     *
     * @param message   the message
     * @param name      the name
     * @param errorCode the error code
     */
    public DAOException(String message, String name, int errorCode) {
        super(message);
        this.name = name;
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name and code.
     *
     * @param message   the message
     * @param cause     the cause
     * @param name      the name
     * @param errorCode the error code
     */
    public DAOException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause);
        this.name = name;
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new exception with the specified cause, name and code.
     *
     * @param cause     the cause
     * @param name      the name
     * @param errorCode the error code
     */
    public DAOException(Throwable cause, String name, int errorCode) {
        super(cause);
        this.name = name;
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name and code,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     * @param name               the name
     * @param errorCode          the error code
     */
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
        this.errorCode = errorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
