package com.epam.esm.handler.validationtemplate.tag;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that tag name has wrong length.
 */
public class TagInvalidNameTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("tagWrongSize");
    }
}
