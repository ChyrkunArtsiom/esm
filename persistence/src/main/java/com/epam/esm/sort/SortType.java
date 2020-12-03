package com.epam.esm.sort;

import com.epam.esm.exception.ExpressionTemplateException;

import java.lang.reflect.InvocationTargetException;

/**
 * Enumeration for getting by which parameter to sort.
 */
public enum SortType {
    /**
     * For sorting by date of creation.
     */
    DATE(CertificateDateExpression.class),
    /**
     * For sorting by the name.
     */
    NAME(CertificateNameExpression.class);

    private Class<? extends ExpressionTemplate> template;

    SortType(Class<? extends ExpressionTemplate> template) {
        this.template = template;
    }

    public ExpressionTemplate getTemplate() {
        try {
            return template.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ExpressionTemplateException("Cannot get template");
        }
    }
}
