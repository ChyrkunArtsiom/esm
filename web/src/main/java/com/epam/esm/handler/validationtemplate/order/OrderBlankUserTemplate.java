package com.epam.esm.handler.validationtemplate.order;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that order doesn't have an user.
 */
public class OrderBlankUserTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("orderUserBlank");
    }
}
