package com.epam.esm.dao.impl;

import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.Entity;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {

    private static TagDAO dao;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext ctx;
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(HikariCPDataSource.class);
        ctx.scan("com.epam.esm");
        ctx.refresh();
        dao = ctx.getBean(TagDAO.class);
    }

    @Test
    void testCreate() {
        Tag tag = new Tag(0, "testtag");
        assertTrue(dao.create(tag));
    }

    @Test
    void testRead() {
        Optional<Tag> optionalTag = dao.read(4);
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