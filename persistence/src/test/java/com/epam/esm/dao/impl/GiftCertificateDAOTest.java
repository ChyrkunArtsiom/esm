package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateDAO.class)
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    void testRead() {
        GiftCertificate certificate = dao.read(1);
        assertNotNull(certificate);
    }

    @Test
    void testReadAll() {
        List<GiftCertificate> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    void testDelete() {
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1.0, null, null, 1, null);
        assertTrue(dao.delete(certificate));
    }

    @Test
    void testCreate() {
        List<String> tags = new ArrayList<>(Arrays.asList("tagtest1", "tagtest2", "tagtest3"));
        GiftCertificate certificate = new GiftCertificate(4, "test", "test",
                1.0, null, null, 1, tags);
        assertNotNull(dao.create(certificate));
    }





    @Test
    void testUpdate() {
        GiftCertificate certificate = new GiftCertificate(5, "testR", "test",
                1.0, null, null, 1, null);
        GiftCertificate oldCertificate = dao.update(certificate);
        assertNotNull(oldCertificate);
    }







}