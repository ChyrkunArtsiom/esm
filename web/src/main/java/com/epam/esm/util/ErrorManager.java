package com.epam.esm.util;

public class ErrorManager {
    private String errorMessage;

    public ErrorManager() {

    }

    public ErrorManager(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
