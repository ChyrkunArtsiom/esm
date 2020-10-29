package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TagDAO.class)
class TagDAOTest {

    @Autowired
    private TagDAO dao;

    @Test
    void testCreate() {
        Tag tag = new Tag(0, "testtag");
        assertTrue(dao.create(tag) != null);
    }

    @Test
    void testReadById() {
        Optional<Tag> optionalTag = dao.read(3);
        assertFalse(optionalTag.isPresent());
    }

    @Test
    void testReadByName() {
        Optional<Tag> optionalTag = dao.read("test");
        assertFalse(optionalTag.isPresent());
    }

    @Test
    void testDelete() {
        Tag tag = new Tag(4, "delete");
        assertTrue(dao.delete(tag));
    }

    @Test
    void testReadAll() {
        List<Tag> tags = dao.readAll();
        assertTrue(tags.size() > 0);
    }
}