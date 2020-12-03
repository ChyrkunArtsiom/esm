package com.epam.esm.handler.validationtemplate.user;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that user second name is not valid.
 */
public class UserInvalidSecondnameTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("userInvalidSecondname");
    }
}
