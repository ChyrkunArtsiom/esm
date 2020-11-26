package com.epam.esm.util;

/**
 * Class-holder of error messages
 */
public class ErrorManager {
    private String errorMessage;

    private int errorCode;

    /**
     * Empty constructor.
     */
    public ErrorManager() {

    }

    /**
     * Constructor with error message.
     *
     * @param errorMessage the string of error message
     */
    public ErrorManager(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
