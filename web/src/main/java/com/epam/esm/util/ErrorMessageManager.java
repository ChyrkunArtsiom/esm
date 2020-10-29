package com.epam.esm.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ErrorMessageManager {
    en_US(ResourceBundle.getBundle("errorMessages", new Locale("en", "US"),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))),

    ru_RU(ResourceBundle.getBundle("errorMessages", new Locale("ru", "RU"))),

    be_BY(ResourceBundle.getBundle("errorMessages", new Locale("be", "BY")));

    private ResourceBundle bundle;

    ErrorMessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
