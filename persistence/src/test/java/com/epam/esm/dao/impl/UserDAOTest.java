package com.epam.esm.dao.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {UserDAO.class, RoleDAO.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Autowired
    private RoleDAO roleDAO;

    @Test
    @Transactional
    public void testCreate() {
        Role role = roleDAO.read("ROLE_USER");
        User user = new User("user", "passowrd".toCharArray(), "Artsiom", "Chyrkun", LocalDate.now(), role);
        assertNotNull(dao.create(user));
    }

    @Test
    public void testReadById() {
        User user = dao.read(1);
        assertNotNull(user);
    }

    @Test
    public void testReadByName() {
        User user = dao.read("admin");
        assertNotNull(user);
    }

    @Test
    public void testReadAll() {
        List<User> users = dao.readAll();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        List<User> tags = dao.readPaginated(page, size);
        assertEquals(size, tags.size());
    }
}