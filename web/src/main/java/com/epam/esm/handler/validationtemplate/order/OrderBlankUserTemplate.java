package com.epam.esm.handler.validationtemplate.order;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

public class OrderBlankUserTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("orderUserBlank");
    }
}
