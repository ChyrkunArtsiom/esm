package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class GiftCertificateDTOValidator {
    public static boolean isValid(GiftCertificateDTO dto) {
        return isNameValid(dto.getName()) && isDescriptionValid(dto.getDescription()) && isPriceValid(dto.getPrice()) &&
                isDatesValid(dto.getCreateDate(), dto.getLastUpdateDate()) && isDurationValid(dto.getDuration());
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

    private static boolean isDatesValid(String createDate, String lastUpdateDate) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime firstDate = OffsetDateTime.parse(createDate, df);
        OffsetDateTime secondDate = OffsetDateTime.parse(lastUpdateDate, df);
        return !firstDate.isBefore(OffsetDateTime.now().with(LocalTime.MIDNIGHT)) && secondDate.isAfter(firstDate);
    }

    private static boolean isDurationValid(Integer duration) {
        return duration > 0;
    }
}
