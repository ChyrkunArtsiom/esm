package com.epam.esm.handler.validationtemplate.user;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that user birthday is not valid.
 */
public class UserInvalidBirthdayTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("userInvalidBirthday");
    }
}
