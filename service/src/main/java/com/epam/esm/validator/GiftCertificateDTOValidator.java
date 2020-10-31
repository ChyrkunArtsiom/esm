package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.CertificateNameIsNotPresentException;
import com.epam.esm.exception.DescriptionIsNotValidException;
import com.epam.esm.exception.DurationIsNotValidException;
import com.epam.esm.exception.PriceIsNotValidException;

import java.math.BigDecimal;

public class GiftCertificateDTOValidator {
    public static boolean isValid(GiftCertificateDTO dto) {
        return isNameValid(dto.getName()) && isDescriptionValid(dto.getDescription()) && isPriceValid(dto.getPrice()) &&
                isDurationValid(dto.getDuration());
    }

    private static boolean isNameValid(String name) {
        if (name.matches("^[a-zA-ZА-Яа-я]{3,45}$")) {
            return true;
        } else {
            throw new CertificateNameIsNotPresentException(name);
        }
    }

    private static boolean isDescriptionValid(String description) {
        if (description.matches("^[\\w\\W]{3,45}$")) {
            return true;
        } else {
            throw new DescriptionIsNotValidException(description);
        }
    }

    private static boolean isPriceValid(BigDecimal price) {
        if (price.intValue() >= 0) {
            return true;
        } else {
            throw new PriceIsNotValidException(String.valueOf(price));
        }
    }

    private static boolean isDurationValid(Integer duration) {
        if (duration > 0) {
            return true;
        } else {
            throw new DurationIsNotValidException(String.valueOf(String.valueOf(duration)));
        }
    }
}
