package com.epam.esm.validator;

/**
 * Class which stores validation messages.
 */
public class ValidationMessageManager {
    /**
     * Indicates that tag name is blank.
     */
    public static final String BLANK_TAG_NAME = "BLANK_TAG_NAME";

    /**
     * Indicates that tag name has wrong length.
     */
    public static final String TAG_INVALID_NAME = "TAG_INVALID_NAME";

    /**
     * Indicates that certificate name is blank.
     */
    public static final String BLANK_CERTIFICATE_NAME = "BLANK_CERTIFICATE_NAME";

    /**
     * Indicates that certificate description is blank.
     */
    public static final String BLANK_CERTIFICATE_DESCRIPTION = "BLANK_CERTIFICATE_DESCRIPTION";

    /**
     * Indicates that certificate name has wrong length.
     */
    public static final String CERTIFICATE_NAME_WRONG_SIZE = "CERTIFICATE_NAME_WRONG_SIZE";

    /**
     * Indicates that certificate description has wrong length.
     */
    public static final String CERTIFICATE_DESCRIPTION_WRONG_SIZE =
            "CERTIFICATE_DESCRIPTION_WRONG_SIZE";

    /**
     * Indicates that certificate price is not valid.
     */
    public static final String CERTIFICATE_PRICE_INVALID =
            "CERTIFICATE_PRICE_INVALID";

    /**
     * Indicates that certificate duration is not valid.
     */
    public static final String CERTIFICATE_DURATION_INVALID =
            "CERTIFICATE_DURATION_INVALID";


    /**
     * Indicates that order doesn't have an user.
     */
    public static final String ORDER_BLANK_USER = "ORDER_BLANK_USER";

    /**
     * Indicates that order doesn't have certificates.
     */
    public static final String ORDER_BLANK_OR_EMPTY_CERTIFICATES = "ORDER_BLANK_OR_EMPTY_CERTIFICATES";

    /**
     * Indicates that id is not valid.
     */
    public static final String ID_INVALID = "ID_INVALID";


}
