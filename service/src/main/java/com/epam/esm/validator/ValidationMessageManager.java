package com.epam.esm.validator;

/**
 * Class which stores validation messages.
 */
public class ValidationMessageManager {
    /**
     * Indicates that tag name is blank.
     */
    public static final String BLANK_TAG_NAME = "Tag cannot be blank.";

    /**
     * Indicates that tag name has wrong length.
     */
    public static final String TAG_NAME_WRONG_SIZE = "Tag has to be from 3 to 45 characters.";

    /**
     * Indicates that certificate name is blank.
     */
    public static final String BLANK_CERTIFICATE_NAME = "Certificate name cannot be blank.";

    /**
     * Indicates that certificate description is blank.
     */
    public static final String BLANK_CERTIFICATE_DESCRIPTION = "Certificate description cannot be blank.";

    /**
     * Indicates that certificate name has wrong length.
     */
    public static final String CERTIFICATE_NAME_WRONG_SIZE = "Certificate name has to be from 3 to 45 characters.";

    /**
     * Indicates that certificate description has wrong length.
     */
    public static final String CERTIFICATE_DESCRIPTION_WRONG_SIZE =
            "Certificate description has to be from 3 to 45 characters.";

    /**
     * Indicates that certificate price is not valid.
     */
    public static final String CERTIFICATE_PRICE_INVALID =
            "Certificate price has to be positive, max digits: 10, fraction: 2.";

    /**
     * Indicates that certificate duration is not valid.
     */
    public static final String CERTIFICATE_DURATION_INVALID =
            "Certificate duration has to be positive max digits: 10, fraction: 0.";


    /**
     * Indicates that order doesn't have an user.
     */
    public static final String ORDER_BLANK_USER = "Order must have user.";

    /**
     * Indicates that order doesn't have certificates.
     */
    public static final String ORDER_BLANK_OR_EMPTY_CERTIFICATES = "Order must have certificates.";

    /**
     * Indicates that id is not valid.
     */
    public static final String ID_INVALID = "Id has to be positive, max digits: 10, fraction: 2.";


}
