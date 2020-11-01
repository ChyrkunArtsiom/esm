package com.epam.esm.handler;

import com.epam.esm.exception.*;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.validator.ValidationMessageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

/**
 * Exception handler class. Constructs a response depending on thrown exception.
 */
@ControllerAdvice
public class EsmExceptionHandler {

    /**
     * Creates {@link EsmExceptionHandler} object for controllers.
     *
     * @return the {@link EsmExceptionHandler} object
     */
    @Bean
    public EsmExceptionHandler handler() {
        return new EsmExceptionHandler();
    }

    /**
     * Handles {@link UnsupportedOperationException} exception.
     *
     * @return the {@link ResponseEntity} object with headers and http status
     */
    @ExceptionHandler({UnsupportedOperationException.class,})
    public ResponseEntity<?> unsupportedOperation() {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.POST);
        headers.setAllow(allowedMethods);
        return new ResponseEntity<>(headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles {@link DuplicateCertificateException} and {@link DuplicateTagException} exceptions.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with headers, {@link ErrorManager} and http status
     */
    @ExceptionHandler({DuplicateCertificateException.class, DuplicateTagException.class})
    public ResponseEntity<ErrorManager> duplicateCertificate(DAOException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("entityAlreadyExists"), ex.getName()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link NoTagException} and {@link NoCertificateException} exceptions.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler({NoTagException.class, NoCertificateException.class})
    public ResponseEntity<ErrorManager> entityNotFound(DAOException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
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

    /**
     * Handles {@link ConstraintViolationException} exception.
     *
     * @param constraintViolationException the exception
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
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

    /**
     * Handles {@link MethodArgumentNotValidException} exception.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorManager> invalidDto(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        StringBuilder responseMessage = new StringBuilder();
        for (ObjectError err : ex.getBindingResult().getAllErrors()) {
            String errorMessage = err.getDefaultMessage();
            if (errorMessage != null) {
                switch (errorMessage) {
                    case ValidationMessageManager.BLANK_TAG_NAME: {
                        responseMessage.append(" ").append(manager.getMessage("blankTag"));
                        break;
                    }
                    case ValidationMessageManager.TAG_NAME_WRONG_SIZE: {
                        responseMessage.append(" ").append(manager.getMessage("tagWrongSize"));
                        break;
                    }
                    case ValidationMessageManager.BLANK_CERTIFICATE_NAME: {
                        responseMessage.append(" ").append(manager.getMessage("certificateNameBlank"));
                        break;
                    }
                    case ValidationMessageManager.BLANK_CERTIFICATE_DESCRIPTION: {
                        responseMessage.append(" ").append(manager.getMessage("certificateDescriptionBlank"));
                        break;
                    }
                    case ValidationMessageManager.CERTIFICATE_NAME_WRONG_SIZE: {
                        responseMessage.append(" ").append(manager.getMessage("certificateNameWrongSize"));
                        break;
                    }
                    case ValidationMessageManager.CERTIFICATE_DESCRIPTION_WRONG_SIZE: {
                        responseMessage.append(" ").append(manager.getMessage("certificateDescriptionWrongSize"));
                        break;
                    }
                    case ValidationMessageManager.CERTIFICATE_PRICE_INVALID: {
                        responseMessage.append(" ").append(manager.getMessage("invalidPrice"));
                        break;
                    }
                    case ValidationMessageManager.CERTIFICATE_DURATION_INVALID: {
                        responseMessage.append(" ").append(manager.getMessage("durationInvalid"));
                        break;
                    }
                }
            }
        }
        error.setErrorMessage(responseMessage.toString().trim());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} exception.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
//Exception that indicates that a method argument has not the expected type.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorManager> argumentWrongType(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("argumentWrongType"), ex.getValue()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ServiceException} exception.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorManager> resourceIsNotValid(ServiceException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("resourceIsNotValid"), ex.getValue()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link CertificateNameIsNotPresentException} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler({CertificateNameIsNotPresentException.class})
    public ResponseEntity<ErrorManager> certificateNameIsNotPresented(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("certificateNameNotPresent"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessageManager setLang(String locale) {
        ErrorMessageManager manager;
        try {
            manager = ErrorMessageManager.valueOf(locale);
        } catch (IllegalArgumentException|NullPointerException ex) {
            manager = ErrorMessageManager.en_US;
        }
        return manager;
    }
}
