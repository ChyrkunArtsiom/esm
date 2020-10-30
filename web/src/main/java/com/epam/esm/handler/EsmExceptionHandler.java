package com.epam.esm.handler;

import com.epam.esm.exception.*;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class EsmExceptionHandler {

    @Bean
    public EsmExceptionHandler handler() {
        return new EsmExceptionHandler();
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

    @ExceptionHandler({DuplicateCertificateException.class, DuplicateTagException.class})
    public ResponseEntity<ErrorManager> duplicateCertificate(DAOException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("entityAlreadyExists"), ex.getName()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoTagException.class, NoCertificateException.class})
    public ResponseEntity<ErrorManager> entityNotFound(DAOException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();
        switch (ex.getErrorCode()) {
            case 40401: {
                error.setErrorMessage(String.format(manager.getMessage("tagDoesntExist"), ex.getName()));
                break;
            }
            case 40402: {
                error.setErrorMessage(String.format(manager.getMessage("certificateDoesntExist"), ex.getName()));
                break;
            }
        }
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    //Invalid path variable exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorManager> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        ErrorManager error = new ErrorManager();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
            errorMessage = builder.toString().trim();
        }
        error.setErrorMessage(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Exception to be thrown when validation on an argument annotated with @Valid fails.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorManager> invalidDto(MethodArgumentNotValidException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();

        StringBuilder responseMessage = new StringBuilder();
        for (ObjectError err : ex.getBindingResult().getAllErrors()) {
            String errorMessage = err.getDefaultMessage();
            if (errorMessage != null) {
                switch (errorMessage) {
                    case ValidationMessageManager.BLANK_TAG: {
                        responseMessage.append(" ").append(manager.getMessage("blankTag"));
                        break;
                    }
                    case ValidationMessageManager.TAG_WRONG_SIZE: {
                        responseMessage.append(" ").append(manager.getMessage("tagWrongSize"));
                        break;
                    }
                }
            }
        }
        error.setErrorMessage(responseMessage.toString().trim());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Exception that indicates that a method argument has not the expected type.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorManager> argumentWrongType(MethodArgumentTypeMismatchException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("argumentWrongType"), ex.getValue()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorManager> resourceIsNotValid(ServiceException ex) {
        String locale = "en_US"; //get locale from somewhere
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("resourceIsNotValid"), ex.getValue()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
