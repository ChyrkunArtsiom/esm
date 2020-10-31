package com.epam.esm.exception;

public class CertificateNameIsNotValidException extends ServiceException {
    public CertificateNameIsNotValidException(String value) {
        super(value);
    }

    public CertificateNameIsNotValidException(String message, String value) {
        super(message, value);
    }

    public CertificateNameIsNotValidException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public CertificateNameIsNotValidException(Throwable cause, String value) {
        super(cause, value);
    }

    public CertificateNameIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
