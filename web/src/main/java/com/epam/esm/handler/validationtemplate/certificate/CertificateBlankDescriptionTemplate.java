package com.epam.esm.handler.validationtemplate.certificate;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

/**
 * The template for indication that certificate description is blank.
 */
public class CertificateBlankDescriptionTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("certificateDescriptionBlank");
    }
}
