package com.epam.esm.exception;

public class DAOException extends RuntimeException {

    private ExceptionType type;
    private Integer id;
    private String name;

    public DAOException(ExceptionType type) {
        this.type = type;
    }

    public DAOException(ExceptionType type, Integer id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public DAOException(String message, ExceptionType type, Integer id, String name) {
        super(message);
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public DAOException(String message, Throwable cause, ExceptionType type, Integer id, String name) {
        super(message, cause);
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public DAOException(Throwable cause, ExceptionType type, Integer id, String name) {
        super(cause);
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionType type, Integer id, String name) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public ExceptionType getType() {
        return type;
    }

    public void setType(ExceptionType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
