package com.epam.esm.handler.validationtemplate.user;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that user first name is blank.
 */
public class UserBlankFirstnameTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("userBlankFirstname");
    }
}
