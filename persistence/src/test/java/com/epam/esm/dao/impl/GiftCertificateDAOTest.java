package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GiftCertificateDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    @Transactional
    public void testCreate() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("thirdtag"));
        GiftCertificate certificate = new GiftCertificate("test3", "test",
                1.0, null, null, 1, tags);
        assertNotNull(dao.create(certificate));
    }

    @Test
    public void testReadById() {
        GiftCertificate certificate = dao.read(1);
        assertNotNull(certificate);
    }

    @Test
    public void testReadByName() {
        GiftCertificate certificate = dao.read("test1");
        assertNotNull(certificate);
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadByParams() {
        GiftCertificate certificate = new GiftCertificate("test1", "Test description 1",
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
    @Transactional
    public void testUpdate() {
        GiftCertificate certificate = dao.read("test1");
        certificate.setDescription("after update");
        Set<Tag> tags = certificate.getTags();
        tags.add(new Tag("newtag"));
        certificate.setTags(tags);
        GiftCertificate oldCertificate = dao.update(certificate);
        assertNotNull(oldCertificate);
    }

    @Test
    @Transactional
    public void testDelete() {
        GiftCertificate certificate = new GiftCertificate("test2", "after updating",
                null, null, null, null, null);
        assertTrue(dao.delete(certificate));
    }
}