package com.epam.esm.exception;

public class OrderHasDuplicateCertificatesException extends RuntimeException {
    public OrderHasDuplicateCertificatesException() {
    }

    public OrderHasDuplicateCertificatesException(String message) {
        super(message);
    }

    public OrderHasDuplicateCertificatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderHasDuplicateCertificatesException(Throwable cause) {
        super(cause);
    }

    public OrderHasDuplicateCertificatesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
