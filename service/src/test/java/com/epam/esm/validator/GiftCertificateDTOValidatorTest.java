package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateDTOValidatorTest {

    @Test
    void isValid() {
        GiftCertificateDTO dto = new GiftCertificateDTO(1, "name", "description", BigDecimal.valueOf(100.5), 10, null);
        assertTrue(GiftCertificateDTOValidator.isValid(dto));
    }
}