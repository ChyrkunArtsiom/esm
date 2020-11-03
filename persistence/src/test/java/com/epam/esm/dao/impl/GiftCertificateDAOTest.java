package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateDAO.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    @Order(1)
    public void testCreate() {
        List<String> tags = new ArrayList<>(Arrays.asList("firsttag", "secondtag"));
        GiftCertificate certificate = new GiftCertificate(0, "test3", "test",
                1.0, null, null, 1, tags);
        assertNotNull(dao.create(certificate));
    }

    @Test
    @Order(2)
    public void testRead() {
        GiftCertificate certificate = dao.read("test3");
        assertNotNull(certificate);
    }

    @Test
    @Order(3)
    @Transactional(readOnly = true)
    public void testReadByParams() {
        GiftCertificate certificate = new GiftCertificate(0, "test3", "test",
                1.0, null, null, 1, null);
        SearchCriteria criteria = new SearchCriteria("", "test", "", "date_desc");
        List<GiftCertificate> certificates = dao.readByParams(criteria);
        assertEquals(certificates.get(0), certificate);
    }

    @Test
    @Order(4)
    @Transactional(readOnly = true)
    public void testReadAll() {
        List<GiftCertificate> certificates = dao.readAll();
        assertEquals(certificates.size(), 3);
    }

    @Test
    @Order(5)
    public void testUpdate() {
        List<String> tags = new ArrayList<>();
        tags.add("firsttag");
        GiftCertificate certificate = new GiftCertificate(0, "test3", "after updating",
                69.0, null, null, 69, tags);
        GiftCertificate oldCertificate = dao.update(certificate);
        assertNotNull(oldCertificate);
    }

    @Test
    @Order(6)
    public void testDelete() {
        GiftCertificate certificate = new GiftCertificate(4, "test3", "after updating",
                69.0, null, null, 69, null);
        assertTrue(dao.delete(certificate));
    }
}