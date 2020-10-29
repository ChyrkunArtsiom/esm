package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;

public class GiftCertificateDTOValidator {
    public static boolean isValid(GiftCertificateDTO dto) {
        return isNameValid(dto.getName()) && isDescriptionValid(dto.getDescription()) && isPriceValid(dto.getPrice()) &&
                isDurationValid(dto.getDuration());
    }

    private static boolean isNameValid(String name) {
        return name.matches("^[a-zA-ZА-Яа-я]{1,45}$");
    }

    private static boolean isDescriptionValid(String description) {
        return description.matches("^[a-zA-ZА-Яа-я]{1,45}$");
    }

    private static boolean isPriceValid(double price) {
        return price >= 0;
    }

    private static boolean isDurationValid(Integer duration) {
        return duration > 0;
    }
}
