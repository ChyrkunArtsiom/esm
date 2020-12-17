package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.NoUserException;
import com.epam.esm.handler.ErrorCodesProvider;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for {@link NoUserException}.
 */
public class UserNotFoundTemplate implements ExceptionTemplate {
    @Override
    public ErrorManager getError(ErrorMessageManager manager, DAOException ex) {
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("userDoesntExist"), ex.getName()));
        error.setErrorCode(ErrorCodesProvider.USER_NOT_FOUND);
        return error;
    }
}
