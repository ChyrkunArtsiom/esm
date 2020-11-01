package com.epam.esm.exception;

/**
 * Thrown to indicate that {@link com.epam.esm.entity.GiftCertificate} object not found.
 */
public class NoCertificateException extends DAOException {
    /**
     * Constructs a new exception with the specified name and code.
     *
     * @param name      the name
     * @param errorCode the error code
     */
    public NoCertificateException(String name, int errorCode) {
        super(name, errorCode);
    }

    /**
     * Constructs a new exception with the specified detail message, name and code.
     *
     * @param message   the message
     * @param name      the name
     * @param errorCode the error code
     */
    public NoCertificateException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name and code.
     *
     * @param message   the message
     * @param cause     the cause
     * @param name      the name
     * @param errorCode the error code
     */
    public NoCertificateException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    /**
     * Constructs a new exception with the specified cause, name and code.
     *
     * @param cause     the cause
     * @param name      the name
     * @param errorCode the error code
     */
    public NoCertificateException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
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
    public NoCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
