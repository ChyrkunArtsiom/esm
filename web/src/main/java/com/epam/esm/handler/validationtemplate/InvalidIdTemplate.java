package com.epam.esm.handler.validationtemplate;

import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that id is not valid.
 */
public class InvalidIdTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("invalidId");
    }
}
