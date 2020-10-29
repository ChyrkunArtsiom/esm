package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.AbstractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateService.class)
class GiftCertificateServiceTest {

    @Autowired
    private AbstractService<GiftCertificate, GiftCertificateDTO> service;

    @Test
    void create() {
        GiftCertificateDTO dto = new GiftCertificateDTO(4, "test", "test",
                1.0, 1);
        assertTrue(service.create(dto) != null);
    }

    @Test
    void read() {
        GiftCertificateDTO certificate = service.read(1);
        assertTrue(certificate.getName().isEmpty());
    }

    @Test
    void update() {
        GiftCertificateDTO certificate = new GiftCertificateDTO(3, "testR", "test",
                1.0, 1);
        Optional<GiftCertificateDTO> oldCertificate = service.update(certificate);
        assertTrue(oldCertificate.isPresent());
    }

    @Test
    void delete() {
        GiftCertificateDTO certificate = new GiftCertificateDTO(4, "test", "test",
                1.0, 1);
        assertTrue(service.delete(certificate));
    }

    @Test
    void readAll() {
        List<GiftCertificateDTO> tags = service.readAll();
        assertTrue(tags.size() > 0);
    }
}