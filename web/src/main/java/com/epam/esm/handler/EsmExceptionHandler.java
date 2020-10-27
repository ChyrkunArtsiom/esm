package com.epam.esm.handler;

import com.epam.esm.exception.DAOException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EsmExceptionHandler {

    @Bean
    public EsmExceptionHandler handler() {
        return new EsmExceptionHandler();
    }

    @ExceptionHandler(DAOException.class)
    public String tagDoesntExistHandler() {
        return "error/404";
    }
}
