package com.epam.esm.dao.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"})
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