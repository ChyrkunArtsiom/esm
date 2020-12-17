package com.epam.esm.handler;


/**
 * Class-container for error codes. First three digits - HTTP status code.
 * Forth digit - entity number: 1 - Tag, 2 - GiftCertificate, 3 - User, 4 - Order.
 * Fifth digit - number of field.
 * Sixth digit - number of error for field.
 */
public class ErrorCodesProvider {
    public final static int TAG_NOT_FOUND = 404100;
    public final static int CERTIFICATE_NOT_FOUND = 404200;
    public final static int USER_NOT_FOUND = 404300;
    public final static int ORDER_NOT_FOUND = 404400;
    public final static int DUPLICATE_ENTITY = 409000;
    public final static int UPDATE_CONFLICT = 409001;
    public final static int BAD_ARGUMENT_URL = 400000;
    public final static int INVALID_FIELD = 400999;
    public final static int CERTIFICATE_FIELD_NOT_PRESENT = 400200;
    public final static int EMPTY_PAGE = 404999;
    public final static int PAGE_PARAM_NOT_PRESENT = 400001;
    public final static int INTERNAL_SERVER_ERROR = 500000;
    public final static int UNAUTHORIZED = 401000;
}
