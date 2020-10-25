package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDTOValidatorTest {

    @Test
    void isValid() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        GiftCertificateDTO dto = new GiftCertificateDTO(1, "name", "description", 100.5, OffsetDateTime.now().format(df),
                OffsetDateTime.now().plusDays(1).format(df), 10);
        assertTrue(GiftCertificateDTOValidator.isValid(dto));
    }
}