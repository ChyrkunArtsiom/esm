package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.*;

import java.lang.reflect.InvocationTargetException;

public enum ExceptionType {
    TAG_NOT_FOUND(NoTagException.class, TagNotFoundTemplate.class),
    CERTIFICATE_NOT_FOUND(NoCertificateException.class, CertificateNotFoundTemplate.class),
    USER_NOT_FOUND(NoUserException.class, UserNotFoundTemplate.class),
    ORDER_NOT_FOUND(NoOrderException.class, OrderNotFoundTemplate.class);

    private Class<? extends DAOException> exceptionClass;
    private Class<? extends ExceptionTemplate> template;

    ExceptionType(Class<? extends DAOException> exceptionClass, Class<? extends ExceptionTemplate> template) {
        this.exceptionClass = exceptionClass;
        this.template = template;
    }

    public static ExceptionType getTemplateByClass(Class<? extends DAOException> classType) {
        for (ExceptionType exceptionType : ExceptionType.values()) {
            if (exceptionType.exceptionClass == classType) {
                return exceptionType;
            }
        }
        throw new NoTemplateException("Template not found");
    }

    public ExceptionTemplate getTemplate() {
        try {
            return template.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new TemplateException("Cannot get template");
        }
    }
}
