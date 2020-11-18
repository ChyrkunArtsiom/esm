package com.epam.esm.exception;

/**
 * Class-holder for error codes.
 */
public class ErrorCodesManager {
    /**
     * Indicates that {@link com.epam.esm.entity.Tag} object not found.
     */
    public final static int TAG_DOESNT_EXIST = 40401;

    /**
     * Indicates that {@link com.epam.esm.entity.GiftCertificate} object not found.
     */
    public final static int CERTIFICATE_DOESNT_EXIST = 40402;

    /**
     * Indicates that there is a duplicate {@link com.epam.esm.entity.Tag} object.
     */
    public final static int DUPLICATE_TAG = 40901;

    /**
     * Indicates that there is a duplicate {@link com.epam.esm.entity.GiftCertificate} object.
     */
    public final static int DUPLICATE_CERTIFICATE = 40902;

    /**
     * Indicates that there is a duplicate bond between {@link com.epam.esm.entity.Tag} and
     * {@link com.epam.esm.entity.GiftCertificate} objects.
     */
    public final static int DUPLICATE_CERTIFICATE_TAG = 40903;

    /**
     * Indicates that {@link com.epam.esm.entity.User} object not found.
     */
    public final static int USER_DOESNT_EXIST = 40403;

    /**
     * Indicates that {@link com.epam.esm.entity.Order} object not found.
     */
    public final static int ORDER_DOESNT_EXIST = 40404;
}
