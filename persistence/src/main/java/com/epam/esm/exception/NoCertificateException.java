package com.epam.esm.exception;

public class NoCertificateException extends DAOException {
    public NoCertificateException(String name, int errorCode) {
        super(name, errorCode);
    }

    public NoCertificateException(String message, String name, int errorCode) {
        super(message, name, errorCode);
    }

    public NoCertificateException(String message, Throwable cause, String name, int errorCode) {
        super(message, cause, name, errorCode);
    }

    public NoCertificateException(Throwable cause, String name, int errorCode) {
        super(cause, name, errorCode);
    }

    public NoCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, name, errorCode);
    }
}
