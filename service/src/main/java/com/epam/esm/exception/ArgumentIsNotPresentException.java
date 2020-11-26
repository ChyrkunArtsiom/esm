package com.epam.esm.exception;

/**
 * Thrown to indicate that there is an argument which has to be presented but isn't.
 */
public class ArgumentIsNotPresentException extends RuntimeException {
    /** Argument which is not presented. */
    private String argument;

    /**
     * Constructs a new exception with the specified name of argument.
     *
     * @param argument the name of argument
     */
    public ArgumentIsNotPresentException(String argument) {
        this.argument = argument;
    }

    /**
     * Constructs a new exception with the specified detail message and name of argument.
     *
     * @param message   the message
     * @param argument  the name of argument
     */
    public ArgumentIsNotPresentException(String message, String argument) {
        super(message);
        this.argument = argument;
    }

    /**
     * Constructs a new exception with the specified detail message, cause and name of argument.
     *
     * @param message  the message
     * @param cause    the cause
     * @param argument the name of argument
     */
    public ArgumentIsNotPresentException(String message, Throwable cause, String argument) {
        super(message, cause);
        this.argument = argument;
    }

    /**
     * Constructs a new exception with the specified cause and name of argument.
     *
     * @param cause    the cause
     * @param argument the name of argument
     */
    public ArgumentIsNotPresentException(Throwable cause, String argument) {
        super(cause);
        this.argument = argument;
    }

    /**
     * Constructs a new exception with the specified detail message, cause, name of argument,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     * @param argument           the name of argument
     */
    public ArgumentIsNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String argument) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
