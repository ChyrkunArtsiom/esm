package com.epam.esm.handler.validationtemplate.certificate;

import com.epam.esm.handler.validationtemplate.ValidationTemplate;
import com.epam.esm.util.ErrorMessageManager;

public class CertificateInvalidPriceTemplate implements ValidationTemplate {
    @Override
    public String getMessage(ErrorMessageManager manager) {
        return manager.getMessage("invalidPrice");
    }
}
