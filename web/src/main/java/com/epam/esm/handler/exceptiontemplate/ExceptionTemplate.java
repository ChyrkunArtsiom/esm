package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

public interface ExceptionTemplate {
    ErrorManager getError(ErrorMessageManager manager, DAOException ex);
}
