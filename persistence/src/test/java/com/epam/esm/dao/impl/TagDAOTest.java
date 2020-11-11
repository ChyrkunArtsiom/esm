package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TagDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TagDAOTest {

    @Autowired
    private TagDAO dao;

    @Test
    @Transactional
    @Rollback(false)
    public void testCreate() {
        Tag tag = new Tag( "thirdtag");
        assertNotNull(dao.create(tag));
    }

    @Test
    public void testReadById() {
        Tag tag = dao.read(1);
        assertNotNull(tag);
    }

    @Test
    public void testReadByName() {
        Tag tag = dao.read("firsttag");
        assertNotNull(tag);
    }

    @Test
    @Transactional
    public void testDelete() {
        Tag tag = new Tag( "thirdtag");
        assertTrue(dao.delete(tag));
    }

    @Test
    public void testReadAll() {
        List<Tag> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }
}