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
     * @return {@code true} if {@link GiftCertificateDTO} object is valid
     */
    public static boolean isValid(GiftCertificateDTO dto) {
        return isNameValid(dto.getName()) && isDescriptionValid(dto.getDescription()) && isPriceValid(dto.getPrice()) &&
                isDurationValid(dto.getDuration());
    }

    /**
     * Validates a name of {@link GiftCertificateDTO} object.
     *
     * @param name the name of {@link GiftCertificateDTO} object
     * @return {@code true} if name is valid
     * @throws CertificateNameIsNotPresentException if name is not valid
     */
    public static boolean isNameValid(String name) {
        if (name.matches("^[a-zA-ZА-Яа-я]{3,45}$")) {
            return true;
        } else {
            throw new CertificateNameIsNotValidException(name);
        }
    }

    /**
     * Validates a description of {@link GiftCertificateDTO} object.
     *
     * @param description the description of {@link GiftCertificateDTO} object.
     * @return {@code true} if description is valid
     * @throws DescriptionIsNotValidException if description is not valid
     */
    public static boolean isDescriptionValid(String description) {
        if (description.matches("^[\\w\\W]{3,45}$")) {
            return true;
        } else {
            throw new DescriptionIsNotValidException(description);
        }
    }

    /**
     * Validates a price of {@link GiftCertificateDTO} object.
     *
     * @param price the price of {@link GiftCertificateDTO} object
     * @return {@code true} if price is valid
     * @throws PriceIsNotValidException if price is not valid
     */
    public static boolean isPriceValid(BigDecimal price) {
        if (price.intValue() >= 0) {
            return true;
        } else {
            throw new PriceIsNotValidException(String.valueOf(price));
        }
    }

    /**
     * Validates a duration of {@link GiftCertificateDTO} object.
     *
     * @param duration the duration of {@link GiftCertificateDTO} object
     * @return {@code true} if duration is valid
     * @throws DurationIsNotValidException if duration is not valid
     */
    public static boolean isDurationValid(Integer duration) {
        if (duration > 0) {
            return true;
        } else {
            throw new DurationIsNotValidException(String.valueOf(String.valueOf(duration)));
        }
    }
}
