package com.epam.esm.exception;

/**
 * Thrown to indicate that there is a {@link com.epam.esm.entity.GiftCertificate} duplicate object.
 */
public class DuplicateCertificateException extends DAOException {
    /**
     * Constructs a new exception with the specified name and code.
     *
     * @param name      the name
     * @param errorCode the error code
     */
    public DuplicateCertificateException(String name, int errorCode) {
        super(name, errorCode);
    }

    /**
     * Constructs a new exception with the specified detail message, name and code.
     *
     * @param message   the message
     * @param name      the name
     * @param errorCode the error code
     */
    public DuplicateCertificateException(String message, String name, int errorCode) {
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
    public DuplicateCertificateException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    /**
     * Constructs a new exception with the specified cause, name and code.
     *
     * @param cause     the cause
     * @param name      the name
     * @param errorCode the error code
     */
    public DuplicateCertificateException(Throwable cause, String name, int errorCode) {
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
    public DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
