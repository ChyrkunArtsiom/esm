package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateService.class)
class GiftCertificateServiceTest {

    @Autowired
    private GiftCertificateService service;

    @Test
    void create() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1.0, OffsetDateTime.now(), OffsetDateTime.now(), 1);
        assertTrue(service.create(certificate) > 0);
    }

    @Test
    void read() {
        Optional<GiftCertificate> certificate = service.read(1);
        assertTrue(certificate.isPresent());
    }

    @Test
    void update() {
        GiftCertificate certificate = new GiftCertificate(3, "testR", "test",
                1.0, OffsetDateTime.now(), OffsetDateTime.now(), 1);
        Optional<GiftCertificate> oldCertificate = service.update(certificate);
        assertTrue(oldCertificate.isPresent());
    }

    @Test
    void delete() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1.0, OffsetDateTime.now(), OffsetDateTime.now(), 1);
        assertTrue(service.delete(certificate));
    }

    @Test
    void readAll() {
        List<GiftCertificate> tags = service.readAll();
        assertTrue(tags.size() > 0);
    }
}