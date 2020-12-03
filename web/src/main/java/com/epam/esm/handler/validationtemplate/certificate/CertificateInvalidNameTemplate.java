package com.epam.esm.handler.validationtemplate.certificate;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that certificate name has wrong length.
 */
public class CertificateInvalidNameTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("certificateNameWrongSize");
    }
}
