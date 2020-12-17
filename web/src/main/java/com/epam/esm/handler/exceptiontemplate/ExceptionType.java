package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.*;

import java.lang.reflect.InvocationTargetException;

/**
 * The enumeration for exceptions which indicate that entity is not found.
 */
public enum ExceptionType {
    /**
     * If tag is not found.
     */
    TAG_NOT_FOUND(NoTagException.class, TagNotFoundTemplate.class),
    /**
     * If certificate is not found.
     */
    CERTIFICATE_NOT_FOUND(NoCertificateException.class, CertificateNotFoundTemplate.class),
    /**
     * If user is not found.
     */
    USER_NOT_FOUND(NoUserException.class, UserNotFoundTemplate.class),
    /**
     * If order is not found.
     */
    ORDER_NOT_FOUND(NoOrderException.class, OrderNotFoundTemplate.class);

    private Class<? extends DAOException> exceptionClass;
    private Class<? extends ExceptionTemplate> template;

    ExceptionType(Class<? extends DAOException> exceptionClass, Class<? extends ExceptionTemplate> template) {
        this.exceptionClass = exceptionClass;
        this.template = template;
    }

    /**
     * Gets exception type by class of thrown exception.
     *
     * @param classType the class of thrown exception
     * @return the exception type
     */
    public static ExceptionType getTemplateByClass(Class<? extends DAOException> classType) {
        for (ExceptionType exceptionType : ExceptionType.values()) {
            if (exceptionType.exceptionClass == classType) {
                return exceptionType;
            }
        }
        throw new NoTemplateException("Template not found");
    }

    /**
     * Gets template from exception type.
     *
     * @return the template
     */
    public ExceptionTemplate getTemplate() {
        try {
            return template.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new TemplateException("Cannot get template");
        }
    }
}
