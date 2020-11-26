package com.epam.esm.handler.validationtemplate;

import com.epam.esm.util.ErrorMessageManager;

public class InvalidIdTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("invalidId");
    }
}
