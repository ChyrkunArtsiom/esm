package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.*;

import java.math.BigDecimal;

/**
 * Class validator for {@link GiftCertificateDTO} objects.
 */
public class GiftCertificateDTOValidator {
    /**
     * Validates {@link GiftCertificateDTO} object.
     *
     * @param dto the {@link GiftCertificateDTO} object to validate
     */
    public static void isValid(GiftCertificateDTO dto) {
        isNameValid(dto.getName());
        isDescriptionValid(dto.getDescription());
        isPriceValid(dto.getPrice());
        isDurationValid(dto.getDuration());
    }

    /**
     * Validates a name of {@link GiftCertificateDTO} object.
     *
     * @param name the name of {@link GiftCertificateDTO} object
     * @throws CertificateNameIsNotPresentException if name is not valid
     */
    public static void isNameValid(String name) {
        if (!name.matches("^[a-zA-ZА-Яа-я]{3,45}$")) {
            throw new CertificateNameIsNotValidException(name);
        }
    }

    /**
     * Validates a description of {@link GiftCertificateDTO} object.
     *
     * @param description the description of {@link GiftCertificateDTO} object.
     * @throws DescriptionIsNotValidException if description is not valid
     */
    public static void isDescriptionValid(String description) {
        if (!description.matches("^[\\w\\W]{3,45}$")) {
            throw new DescriptionIsNotValidException(description);
        }
    }

    /**
     * Validates a price of {@link GiftCertificateDTO} object.
     *
     * @param price the price of {@link GiftCertificateDTO} object
     * @throws PriceIsNotValidException if price is not valid
     */
    public static void isPriceValid(BigDecimal price) {
        if (price.intValue() < 0) {
            throw new PriceIsNotValidException(String.valueOf(price));
        }
    }

    /**
     * Validates a duration of {@link GiftCertificateDTO} object.
     *
     * @param duration the duration of {@link GiftCertificateDTO} object
     * @throws DurationIsNotValidException if duration is not valid
     */
    public static void isDurationValid(Integer duration) {
        if (duration <= 0) {
            throw new DurationIsNotValidException(String.valueOf(String.valueOf(duration)));
        }
    }
}
