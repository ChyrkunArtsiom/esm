package com.epam.esm.dao.impl;

import com.epam.esm.PersistenceConfiguration;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*@SpringBootTest(classes = TagDAO.class)*/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceTestConfiguration.class)
/*@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)*/
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
    public void testReadByName() {
        Tag tag = dao.read("firsttag");
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
        List<Tag> tags = dao.readPaginated(page, size);
        assertEquals(size, tags.size());
    }
}