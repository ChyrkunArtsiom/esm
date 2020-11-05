package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.CertificateNameIsNotValidException;
import com.epam.esm.exception.DescriptionIsNotValidException;
import com.epam.esm.exception.DurationIsNotValidException;
import com.epam.esm.exception.PriceIsNotValidException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDTOValidatorTest {

    @Test
    public void testIsValid() {
        GiftCertificateDTO dto = new GiftCertificateDTO(1, "name", "description", BigDecimal.valueOf(100.5), 10, null);
        assertDoesNotThrow(() -> GiftCertificateDTOValidator.isValid(dto));
    }

    @Test
    public void testInvalidName() {
        GiftCertificateDTO dto = new GiftCertificateDTO
                (1, "tt", "description", BigDecimal.valueOf(100.5), 10, null);
        CertificateNameIsNotValidException thrown = assertThrows(CertificateNameIsNotValidException.class,
                () -> GiftCertificateDTOValidator.isValid(dto));
        assertEquals(dto.getName(), thrown.getValue());
    }

    @Test
    public void testInvalidDescription() {
        GiftCertificateDTO dto = new GiftCertificateDTO
                (1, "name", "tt", BigDecimal.valueOf(100.5), 10, null);
        DescriptionIsNotValidException thrown = assertThrows(DescriptionIsNotValidException.class,
                () -> GiftCertificateDTOValidator.isValid(dto));
        assertEquals(dto.getDescription(), thrown.getValue());
    }

    @Test
    public void testInvalidPrice() {
        GiftCertificateDTO dto = new GiftCertificateDTO
                (1, "name", "description", BigDecimal.valueOf(-1), 10, null);
        PriceIsNotValidException thrown = assertThrows(PriceIsNotValidException.class,
                () -> GiftCertificateDTOValidator.isValid(dto));
        assertEquals(dto.getPrice().toString(), thrown.getValue());
    }

    @Test
    public void testInvalidDuration() {
        GiftCertificateDTO dto = new GiftCertificateDTO
                (1, "name", "description", BigDecimal.valueOf(1), -1, null);
        DurationIsNotValidException thrown = assertThrows(DurationIsNotValidException.class,
                () -> GiftCertificateDTOValidator.isValid(dto));
        assertEquals(dto.getDuration().toString(), thrown.getValue());
    }
}