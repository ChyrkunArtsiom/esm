package com.epam.esm.validator;

public class ValidationMessageManager {
    public static final String BLANK_TAG_NAME = "Tag cannot be blank.";
    public static final String TAG_NAME_WRONG_SIZE = "Tag has to be from 3 to 45 characters.";
    public static final String BLANK_CERTIFICATE_NAME = "Certificate name cannot be blank.";
    public static final String BLANK_CERTIFICATE_DESCRIPTION = "Certificate description cannot be blank.";
    public static final String CERTIFICATE_NAME_WRONG_SIZE = "Certificate name has to be from 3 to 45 characters.";
    public static final String CERTIFICATE_DESCRIPTION_WRONG_SIZE =
            "Certificate description has to be from 3 to 45 characters.";
    public static final String CERTIFICATE_PRICE_INVALID =
            "Certificate price has to be positive, max digits: 10, fraction: 2.";
    public static final String CERTIFICATE_DURATION_INVALID =
            "Certificate duration has to be positive max digits: 10, fraction: 0.";
}
