package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The interface for marking templates for handling exceptions
 * which indicate if entity is not found.
 */
public interface ExceptionTemplate {
    /**
     * Gets error from template.
     *
     * @param manager the {@link ErrorMessageManager} object
     * @param ex      the {@link DAOException} object
     * @return the {@link ErrorManager} object
     */
    ErrorManager getError(ErrorMessageManager manager, DAOException ex);
}
