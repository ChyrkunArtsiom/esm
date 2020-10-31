package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class GiftCertificateMapper {

    public static GiftCertificate toEntity(GiftCertificateDTO dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        if (dto.getPrice() != null) {
            certificate.setPrice(dto.getPrice().doubleValue());
        } else {
            certificate.setPrice(0.0);
        }
        certificate.setDuration(dto.getDuration());
        certificate.setTags(dto.getTags());
        return certificate;
    }

    public static GiftCertificateDTO toDto(GiftCertificate entity) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(BigDecimal.valueOf(entity.getPrice()));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dto.setCreateDate(entity.getCreateDate().format(df));
        if (entity.getLastUpdateDate() != null) {
            dto.setLastUpdateDate(entity.getLastUpdateDate().format(df));
        }
        dto.setDuration(entity.getDuration());
        dto.setTags(entity.getTags());
        return dto;
    }
}
