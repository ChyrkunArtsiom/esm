package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TagService.class)
class GiftCertificateServiceTest {

    @Autowired
    private GiftCertificateService service;

    @Test
    void create() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        assertTrue(service.create(certificate));
    }

    @Test
    void read() {
        Optional<GiftCertificate> certificate = service.read(1);
        assertTrue(certificate.isPresent());
    }

    @Test
    void update() {
        GiftCertificate certificate = new GiftCertificate(3, "testR", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        Optional<GiftCertificate> oldCertificate = service.update(certificate);
        assertTrue(oldCertificate.isPresent());
    }

    @Test
    void delete() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        assertTrue(service.delete(certificate));
    }

    @Test
    void readAll() {
        List<GiftCertificate> tags = service.readAll();
        assertTrue(tags.size() > 0);
    }
}