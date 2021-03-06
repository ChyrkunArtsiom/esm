package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"})
class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Test
    @Transactional
    public void testCreate() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("TagName"));
        GiftCertificate certificate = new GiftCertificate("Name", "test",
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
        SearchCriteria criteria = new SearchCriteria("firsttag,secondtag", "tes", "Test", "name_asc");
        List<GiftCertificate> certificates = dao.readByParams(criteria, 1, 1);
        assertEquals(certificate.getName(), certificates.get(0).getName());
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadAll() {
        List<GiftCertificate> certificates = dao.readAll();
        assertEquals(4, certificates.size());
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

    @Test
    @Transactional
    public void testDeleteById() {
        assertTrue(dao.delete(1));
    }
}