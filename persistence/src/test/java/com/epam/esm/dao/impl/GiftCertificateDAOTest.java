package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    public void testCreate() {
        List<String> tags = new ArrayList<>(Arrays.asList("firsttag", "secondtag"));
        GiftCertificate certificate = new GiftCertificate(0, "test3", "test",
                1.0, null, null, 1, tags);
        assertNotNull(dao.create(certificate));
    }

    @Test
    public void testRead() {
        GiftCertificate certificate = dao.read("test2");
        assertNotNull(certificate);
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadByParams() {
        GiftCertificate certificate = new GiftCertificate(0, "test1", "Test description 1",
                1.0, null, null, 1, null);
        SearchCriteria criteria = new SearchCriteria("", "test1", "", "date_desc");
        List<GiftCertificate> certificates = dao.readByParams(criteria);
        assertEquals(certificates.get(0), certificate);
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadAll() {
        List<GiftCertificate> certificates = dao.readAll();
        assertEquals(certificates.size(), 2);
    }

    @Test
    public void testUpdate() {
        List<String> tags = new ArrayList<>();
        tags.add("firsttag");
        GiftCertificate certificate = new GiftCertificate(0, "test1", "after updating",
                69.0, null, null, 69, tags);
        GiftCertificate oldCertificate = dao.update(certificate);
        assertNotNull(oldCertificate);
    }

    @Test
    public void testDelete() {
        GiftCertificate certificate = new GiftCertificate(4, "test2", "after updating",
                null, null, null, null, null);
        assertTrue(dao.delete(certificate));
    }
}