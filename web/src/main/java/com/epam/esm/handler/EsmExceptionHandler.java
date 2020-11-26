package com.epam.esm.handler;

import com.epam.esm.exception.*;
import com.epam.esm.handler.exceptiontemplate.ExceptionType;
import com.epam.esm.handler.validationtemplate.ValidationMessageType;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;
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
    public ResponseEntity<ErrorManager> duplicateEntity(DAOException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("entityAlreadyExists"), ex.getName()));
        error.setErrorCode(ErrorCodesProvider.DUPLICATE_ENTITY);
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
        ExceptionType type = ExceptionType.getTemplateByClass(ex.getClass());
        ErrorManager error = type.getTemplate().getError(manager, ex);
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
        error.setErrorCode(ErrorCodesProvider.BAD_ARGUMENT_URL);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link StaleObjectStateException} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<ErrorManager> updateConflict(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("updateConflict"));
        error.setErrorCode(ErrorCodesProvider.UPDATE_CONFLICT);
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
                responseMessage
                        .append(" ")
                        .append(ValidationMessageType.valueOf(errorMessage).getTemplate().getMessage(manager));
            }
        }
        error.setErrorMessage(responseMessage.toString().trim());
        error.setErrorCode(ErrorCodesProvider.INVALID_FIELD);
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
        error.setErrorCode(ErrorCodesProvider.BAD_ARGUMENT_URL);
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
        error.setErrorCode(ErrorCodesProvider.INVALID_FIELD);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ArgumentIsNotPresentException} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler({ArgumentIsNotPresentException.class})
    public ResponseEntity<ErrorManager> certificateFieldNotPresent(ArgumentIsNotPresentException ex, WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("argumentNotPresent"), ex.getArgument()));
        error.setErrorCode(ErrorCodesProvider.CERTIFICATE_FIELD_NOT_PRESENT);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotFoundException} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorManager> resourceNotFound(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("resourceNotFound"));
        error.setErrorCode(ErrorCodesProvider.EMPTY_PAGE);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link PageParamIsNotPresent} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(PageParamIsNotPresent.class)
    public ResponseEntity<ErrorManager> getParameterIsNotPresent(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("pageParamIsNotPresent"));
        error.setErrorCode(ErrorCodesProvider.PAGE_PARAM_NOT_PRESENT);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link TemplateException} exception.
     *
     * @param request the {@link WebRequest} object
     * @return the {@link ResponseEntity} object with {@link ErrorManager} and http status
     */
    @ExceptionHandler(TemplateException.class)
    public ResponseEntity<ErrorManager> templateCannotBeCreated(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("internalServerError"));
        error.setErrorCode(ErrorCodesProvider.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderHasDuplicateCertificatesException.class)
    public ResponseEntity<ErrorManager> orderHasDuplicateCertificates(WebRequest request) {
        ErrorMessageManager manager = setLang(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(manager.getMessage("orderHasDuplicateCertificates"));
        error.setErrorCode(ErrorCodesProvider.INVALID_FIELD);
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
