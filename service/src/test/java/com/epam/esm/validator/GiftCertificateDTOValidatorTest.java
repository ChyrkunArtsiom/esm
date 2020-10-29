package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateDTOValidatorTest {

    @Test
    void isValid() {
        GiftCertificateDTO dto = new GiftCertificateDTO(1, "name", "description", 100.5, 10);
        assertTrue(GiftCertificateDTOValidator.isValid(dto));
    }
}