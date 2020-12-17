package com.epam.esm.handler.exceptiontemplate;

import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.handler.ErrorCodesProvider;
import com.epam.esm.util.ErrorManager;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for {@link NoCertificateException}.
 */
public class CertificateNotFoundTemplate implements ExceptionTemplate {
    @Override
    public ErrorManager getError(ErrorMessageManager manager, DAOException ex) {
        ErrorManager error = new ErrorManager();
        error.setErrorMessage(String.format(manager.getMessage("certificateDoesntExist"), ex.getName()));
        error.setErrorCode(ErrorCodesProvider.CERTIFICATE_NOT_FOUND);
        return error;
    }
}
