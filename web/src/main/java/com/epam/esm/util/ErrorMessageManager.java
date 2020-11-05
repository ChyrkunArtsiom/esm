package com.epam.esm.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Enumeration for getting localized messages from bundle.
 */
public enum ErrorMessageManager {
    /** The en_US bundle. This is default bundle. */
    en_US(ResourceBundle.getBundle("errorMessages", new Locale("en", "US"),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))),

    /** The ru_RU bundle. */
    ru_RU(ResourceBundle.getBundle("errorMessages", new Locale("ru", "RU"))),

    /** The be_BY bundle. */
    be_BY(ResourceBundle.getBundle("errorMessages", new Locale("be", "BY")));

    private ResourceBundle bundle;

    ErrorMessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Gets name of message. Returns a message.
     *
     * @param key the name of message
     * @return the string of message
     */
    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
