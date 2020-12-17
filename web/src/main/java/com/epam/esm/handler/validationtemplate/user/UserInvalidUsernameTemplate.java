package com.epam.esm.handler.validationtemplate.user;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that user name is not valid.
 */
public class UserInvalidUsernameTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("userInvalidUsername");
    }
}
