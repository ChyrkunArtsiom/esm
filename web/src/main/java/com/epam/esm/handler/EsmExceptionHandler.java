package com.epam.esm.handler;

import com.epam.esm.exception.*;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.validator.ValidationMessageManager;
import org.hibernate.StaleObjectStateException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
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
    @ExceptionHandler({NoTagException.class, NoCertificateException.class, NoUserException.class, NoOrderException.class})
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
            case 40403: {
                error.setErrorMessage(String.format(manager.getMessage("userDoesntExist"), ex.getName()));
                break;
            }
            case 40404: {
                error.setErrorMessage(String.format(manager.getMessage("orderDoesntExist"), ex.getName()));
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
    public ResponseEntity<ErrorManager> invalidBody(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        ErrorManager error = new ErrorManager();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(((PathImpl)violation.getPropertyPath()).getLeafNode().getName())
                    .append(" ").append(violation.getMessage()).append(". "));
            errorMessage = builder.toString().trim();
        }
        error.setErrorMessage(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<ErrorManager> updateConflict(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("updateConflict"));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
                    case ValidationMessageManager.ORDER_BLANK_USER: {
                        responseMessage.append(" ").append(manager.getMessage("orderUserBlank"));
                        break;
                    }
                    case ValidationMessageManager.ORDER_BLANK_OR_EMPTY_CERTIFICATES: {
                        responseMessage.append(" ").append(manager.getMessage("orderCertificatesBlank"));
                        break;
                    }
                    case ValidationMessageManager.ID_INVALID: {
                        responseMessage.append(" ").append(manager.getMessage("invalidId"));
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
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorManager> argumentWrongType(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("argumentWrongType"), ex.getValue()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link OrderHasMissingArgumentException} exception.
     *
     * @param ex      the exception
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(OrderHasMissingArgumentException.class)
    public ResponseEntity<ErrorManager> orderHasMissingArguments(ServiceException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("orderHasMissingArguments"), ex.getValue()));
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
     * Handles {@link ArgumentIsNotPresent} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler({ArgumentIsNotPresent.class})
    public ResponseEntity<ErrorManager> certificateNameIsNotPresented(ArgumentIsNotPresent ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("argumentNotPresent"), ex.getArgument()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorManager> resourceNotFound(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("resourceNotFound"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GetParamIsNotPresent.class)
    public ResponseEntity<ErrorManager> getParameterIsNotPresent(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("getParamIsNotPresent"));
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
