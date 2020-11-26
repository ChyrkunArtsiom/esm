package com.epam.esm.handler.validationtemplate;

import com.epam.esm.util.ErrorMessageManager;

public interface ValidationTemplate {
    String getMessage(ErrorMessageManager manager);
}
