package com.epam.esm.exception;

public class DuplicateCertificateException extends DAOException {
    public DuplicateCertificateException(String name, int errorCode) {
        super(name, errorCode);
    }

    public DuplicateCertificateException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    public DuplicateCertificateException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    public DuplicateCertificateException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
    }

    public DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
