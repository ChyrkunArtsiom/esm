package com.epam.esm.handler.validationtemplate;

import com.epam.esm.util.ErrorMessageManager;

/**
 * The interface for marking templates for handling validation exceptions.
 */
public interface ValidationTemplate {
    /**
     * Gets message.
     *
     * @param manager the manager
     * @return the message
     */
    String getMessage(ErrorMessageManager manager);
}
