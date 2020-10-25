package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateMapperTest {
    private static GiftCertificateDTO dto;
    private static GiftCertificate entity;
    private static GiftCertificateMapper mapper = new GiftCertificateMapper();

    @BeforeAll
    static void setUp() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        dto = new GiftCertificateDTO(1, "Test certificate", "Test description",
                100.0, date.toString(),
                date.plusDays(1).toString(), 10);
        entity = new GiftCertificate(1, "Test certificate", "Test description",
                100.0, date,
                date.plusDays(1), 10);
    }

    @Test
    void testToEntity() {
        GiftCertificate converted = mapper.toEntity(dto);
        assertEquals(entity, converted);
    }

    @Test
    void testToDto() {
        GiftCertificateDTO converted = mapper.toDto(entity);
        assertEquals(dto, converted);
    }
}