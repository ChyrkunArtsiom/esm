package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that converts {@link GiftCertificate} and {@link GiftCertificateDTO} objects to each other.
 */
public class GiftCertificateMapper {

    /**
     * Converts {@link GiftCertificateDTO} object to a {@link GiftCertificate}.
     *
     * @param dto the {@link GiftCertificateDTO} object
     * @return the {@link GiftCertificate} object
     */
    public static GiftCertificate toEntity(GiftCertificateDTO dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        if (dto.getPrice() != null) {
            certificate.setPrice(dto.getPrice().doubleValue());
        } else {
            certificate.setPrice(0.0);
        }
        certificate.setDuration(dto.getDuration());
        Set<Tag> tags = new HashSet<>();
        if (dto.getTags() != null) {
            for (TagDTO tag : dto.getTags()) {
                tags.add(TagMapper.toEntity(tag));
            }
        }
        certificate.setTags(tags);
        return certificate;
    }

    /**
     * Converts {@link GiftCertificate} object to a {@link GiftCertificateDTO}.
     *
     * @param entity the {@link GiftCertificate} object
     * @return the {@link GiftCertificateDTO} object
     */
    public static GiftCertificateDTO toDto(GiftCertificate entity) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(BigDecimal.valueOf(entity.getPrice()));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        if (entity.getCreateDate() != null) {
            dto.setCreateDate(entity.getCreateDate().format(df));
        }
        if (entity.getLastUpdateDate() != null) {
            dto.setLastUpdateDate(entity.getLastUpdateDate().format(df));
        }
        dto.setDuration(entity.getDuration());
        Set<TagDTO> tags = new HashSet<>();
        if (entity.getTags() != null) {
            for (Tag tag : entity.getTags()) {
                tags.add(TagMapper.toDto(tag));
            }
        }
        dto.setTags(tags);
        return dto;
    }
}
