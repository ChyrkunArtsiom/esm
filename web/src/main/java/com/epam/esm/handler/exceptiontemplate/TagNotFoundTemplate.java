package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.handler.ErrorCodesProvider;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

public class TagNotFoundTemplate implements ExceptionTemplate {
    @Override
    public ErrorManager getError(ErrorMessageManager manager, DAOException ex) {
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("tagDoesntExist"), ex.getName()));
        error.setErrorCode(ErrorCodesProvider.TAG_NOT_FOUND);
        return error;
    }
}
