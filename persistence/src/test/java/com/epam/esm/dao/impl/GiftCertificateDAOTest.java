package com.epam.esm.dao.impl;

import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateDAO.class)
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    void testCreate() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        assertTrue(dao.create(certificate));
    }

    @Test
    void testRead() {
        Optional<GiftCertificate> certificate = dao.read(1);
        assertTrue(certificate.isPresent());
    }

    @Test
    void testUpdate() {
        GiftCertificate certificate = new GiftCertificate(3, "testR", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        Optional<GiftCertificate> oldCertificate = dao.update(certificate);
        assertTrue(oldCertificate.isPresent());
    }

    @Test
    void testDelete() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1f, LocalDateTime.now(), LocalDateTime.now(), 1);
        assertTrue(dao.delete(certificate));
    }

    @Test
    void testReadAll() {
        List<GiftCertificate> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }

}