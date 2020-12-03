package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.NoOrderException;
import com.epam.esm.handler.ErrorCodesProvider;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for {@link NoOrderException}.
 */
public class OrderNotFoundTemplate implements ExceptionTemplate {
    @Override
    public ErrorManager getError(ErrorMessageManager manager, DAOException ex) {
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("orderDoesntExist"), ex.getName()));
        error.setErrorCode(ErrorCodesProvider.ORDER_NOT_FOUND);
        return error;
    }
}
