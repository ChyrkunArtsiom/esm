package com.epam.esm.exception;

public class CertificateNameIsNotPresentException extends ServiceException {
    public CertificateNameIsNotPresentException(String value) {
        super(value);
    }

    public CertificateNameIsNotPresentException(String message, String value) {
        super(message, value);
    }

    public CertificateNameIsNotPresentException(String message, Throwable cause, String value) {
        super(message, cause, value);
    }

    public CertificateNameIsNotPresentException(Throwable cause, String value) {
        super(cause, value);
    }

    public CertificateNameIsNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String value) {
        super(message, cause, enableSuppression, writableStackTrace, value);
    }
}
