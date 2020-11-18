package com.epam.esm.dao.impl;

import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Test
    void testReadById() {
        User user = dao.read(1);
        assertNotNull(user);
    }

    @Test
    void testReadByName() {
        User user = dao.read("user1");
        assertNotNull(user);
    }

    @Test
    void testReadAll() {
        List<User> users = dao.readAll();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        List<User> tags = dao.readPaginated(page, size);
        assertEquals(tags.size(), size);
    }
}