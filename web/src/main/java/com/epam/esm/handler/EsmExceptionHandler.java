package com.epam.esm.handler;

import com.epam.esm.exception.DAOException;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class EsmExceptionHandler {

    @Bean
    public EsmExceptionHandler handler() {
        return new EsmExceptionHandler();
    }

    @ExceptionHandler(DAOException.class)
    public ResponseEntity<ErrorManager> entityDoesntExist(DAOException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();
        ResponseEntity<ErrorManager> responseEntity;

        switch (ex.getType()) {
            case TAG_DOESNT_EXIST: {
                error.setErrorMessage(String.format(manager.getMessage("tagDoesntExist"), ex.getId()));
                responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                break;
            }
            case CERTIFICATE_DOESNT_EXIST: {
                error.setErrorMessage(String.format(manager.getMessage("certificateDoesntExist"), ex.getId()));
                responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                break;
            }
            case DUPLICATE_ENTITY: {
                error.setErrorMessage(String.format(manager.getMessage("entityAlreadyExists"), ex.getName()));
                responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
                break;
            }
            default: {
                responseEntity = null;
            }
        }
        return responseEntity;
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> unsupportedOperation() {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.POST);
        headers.setAllow(allowedMethods);
        return new ResponseEntity<>(headers, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
