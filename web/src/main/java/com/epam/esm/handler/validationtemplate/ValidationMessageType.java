package com.epam.esm.handler.validationtemplate;

import com.epam.esm.exception.TemplateException;
import com.epam.esm.handler.validationtemplate.certificate.*;
import com.epam.esm.handler.validationtemplate.order.OrderBlankCertificateTemplate;
import com.epam.esm.handler.validationtemplate.order.OrderBlankUserTemplate;
import com.epam.esm.handler.validationtemplate.tag.TagBlankNameTemplate;
import com.epam.esm.handler.validationtemplate.tag.TagInvalidNameTemplate;
import com.epam.esm.handler.validationtemplate.user.*;

import java.lang.reflect.InvocationTargetException;

/**
 * The enumeration for different types of validation exceptions.
 */
public enum ValidationMessageType {
    /**
     * Indicates that tag name is blank.
     */
    BLANK_TAG_NAME(TagBlankNameTemplate.class),

    /**
     * Indicates that tag name has wrong length.
     */
    TAG_INVALID_NAME(TagInvalidNameTemplate.class),

    /**
     * Indicates that certificate name is blank.
     */
    BLANK_CERTIFICATE_NAME(CertificateBlankNameTemplate.class),

    /**
     * Indicates that certificate description is blank.
     */
    BLANK_CERTIFICATE_DESCRIPTION(CertificateBlankDescriptionTemplate.class),

    /**
     * Indicates that certificate name has wrong length.
     */
    CERTIFICATE_NAME_WRONG_SIZE(CertificateInvalidNameTemplate.class),

    /**
     * Indicates that certificate description has wrong length.
     */
    CERTIFICATE_DESCRIPTION_WRONG_SIZE(CertificateInvalidDescriptionTemplate.class),

    /**
     * Indicates that certificate price is not valid.
     */
    CERTIFICATE_PRICE_INVALID(CertificateInvalidPriceTemplate.class),

    /**
     * Indicates that certificate duration is not valid.
     */
    CERTIFICATE_DURATION_INVALID(CertificateInvalidDurationTemplate.class),


    /**
     * Indicates that order doesn't have an user.
     */
    ORDER_BLANK_USER(OrderBlankUserTemplate.class),

    /**
     * Indicates that order doesn't have certificates.
     */
    ORDER_BLANK_OR_EMPTY_CERTIFICATES(OrderBlankCertificateTemplate.class),

    /**
     * Indicates that id is not valid.
     */
    ID_INVALID(InvalidIdTemplate.class),

    /**
     * Indicates that user name is blank.
     */
    USER_BLANK_USERNAME(UserBlankUsernameTemplate.class),

    /**
     * Indicates that user password is blank.
     */
    USER_BLANK_PASSWORD(UserBlankPasswordTemplate.class),

    /**
     * Indicates that user first name is blank.
     */
    USER_BLANK_FIRSTNAME(UserBlankFirstnameTemplate.class),

    /**
     * Indicates that user second name is blank.
     */
    USER_BLANK_SECONDNAME(UserBlankSecondnameTemplate.class),

    /**
     * Indicates that user birthday is blank.
     */
    USER_BLANK_BIRTHDAY(UserBlankBirthdayTemplate.class),

    /**
     * Indicates that user name is not valid.
     */
    USER_INVALID_USERNAME(UserInvalidUsernameTemplate.class),

    /**
     * Indicates that user password is not valid.
     */
    USER_INVALID_PASSWORD(UserInvalidPasswordTemplate.class),

    /**
     * Indicates that user first name is not valid.
     */
    USER_INVALID_FIRSTNAME(UserInvalidFirstnameTemplate.class),

    /**
     * Indicates that user second name is not valid.
     */
    USER_INVALID_SECONDNAME(UserInvalidSecondnameTemplate.class),

    /**
     * Indicates that user birthday is not valid.
     */
    USER_INVALID_BIRTHDAY(UserInvalidBirthdayTemplate.class);

    private Class<? extends ValidationTemplate> template;

    ValidationMessageType(Class<? extends ValidationTemplate> template) {
        this.template = template;
    }

    public ValidationTemplate getTemplate() {
        try {
            return template.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new TemplateException("Cannot get template");
        }
    }
}
