package com.epam.esm.dao.impl;

import com.epam.esm.PersistenceConfiguration;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"})
class TagDAOTest {

    @Autowired
    private TagDAO dao;

    @Test
    @Transactional
    public void testCreate() {
        Tag tag = new Tag( "fifth");
        assertNotNull(dao.create(tag));
    }

    @Test
    public void testReadById() {
        Tag tag = dao.read(1);
        assertNotNull(tag);
    }

    @Test
    public void testReadAll() {
        List<Tag> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    public void testReadByParams() {
        SearchCriteria criteria = new SearchCriteria(null , "FIRST", null, null);
        List<Tag> tags = dao.readPaginated(criteria, 1, 5);
        assertEquals(1, tags.size());
    }

    @Test
    public void testReadByName() {
        Tag tag = dao.read("thirdtag");
        assertNotNull(tag);
    }

    @Test
    @Transactional
    public void testDelete() {
        Tag tag = new Tag( "secondtag");
        assertTrue(dao.delete(tag));
    }

    @Test
    @Transactional
    public void testDeleteById() {
        assertTrue(dao.delete(1));
    }

    @Test
    public void testReadPaginated() {
        int page = 2;
        int size = 2;
        List<Tag> tags = dao.readPaginated(new SearchCriteria("", "", "", "name_asc"), page, size);
        assertEquals(size, tags.size());
    }
}