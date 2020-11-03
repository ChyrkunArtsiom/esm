package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TagDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TagDAOTest {

    @Autowired
    private TagDAO dao;

    @Test
    void testCreate() {
        Tag tag = new Tag(3, "thirdtag");
        assertNotNull(dao.create(tag));
    }

    @Test
    void testReadById() {
        Tag tag = dao.read(1);
        assertNotNull(tag);
    }

    @Test
    void testReadByName() {
        Tag tag = dao.read("firsttag");
        assertNotNull(tag);
    }

    @Test
    void testDelete() {
        Tag tag = new Tag(3, "thirdtag");
        assertTrue(dao.delete(tag));
    }

    @Test
    void testReadAll() {
        List<Tag> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }
}