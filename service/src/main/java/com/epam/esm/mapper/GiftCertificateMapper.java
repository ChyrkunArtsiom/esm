package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class GiftCertificateMapper {

    public static GiftCertificate toEntity(GiftCertificateDTO dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setCreateDate(OffsetDateTime.parse(dto.getCreateDate()));
        certificate.setLastUpdateDate(OffsetDateTime.parse(dto.getLastUpdateDate()));
        certificate.setDuration(dto.getDuration());
        return certificate;
    }

    public static GiftCertificateDTO toDto(GiftCertificate entity) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dto.setCreateDate(entity.getCreateDate().format(df));
        dto.setLastUpdateDate(entity.getLastUpdateDate().format(df));
        dto.setDuration(entity.getDuration());
        return dto;
    }
}
