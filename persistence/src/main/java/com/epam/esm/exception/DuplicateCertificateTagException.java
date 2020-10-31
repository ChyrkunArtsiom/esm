package com.epam.esm.exception;

public class DuplicateCertificateTagException extends DAOException {
    public DuplicateCertificateTagException(String name, int errorCode) {
        super(name, errorCode);
    }

    public DuplicateCertificateTagException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    public DuplicateCertificateTagException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    public DuplicateCertificateTagException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
    }

    public DuplicateCertificateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
