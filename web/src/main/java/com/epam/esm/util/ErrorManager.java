package com.epam.esm.util;

/**
 * Class-holder of error messages
 */
public class ErrorManager {
    private String errorMessage;

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
