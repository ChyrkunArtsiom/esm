package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"})
class OrderDAOTest {

    @Autowired
    private OrderDAO dao;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GiftCertificateDAO certificateDAO;

    @Test
    @Transactional
    public void testCreate() {
        User user = userDAO.read(1);
        GiftCertificate certificate = certificateDAO.read(1);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        Order order = new Order( 10.0, date, user, Arrays.asList(certificate));
        assertNotNull(dao.create(order));
    }

    @Test
    public void testRead() {
        Order order = dao.read(1);
        assertNotNull(order);
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadAll() {
        List<Order> certificates = dao.readAll();
        assertEquals(3, certificates.size());
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 1;
        List<Order> tags = dao.readPaginated(page, size);
        assertEquals(size, tags.size());
    }

    @Test
    @Transactional
    public void testUpdate() {
        Order order = dao.read(1);
        User user = userDAO.read(2);
        order.setUser(user);
        Order oldOrder = dao.update(order);
        assertNotNull(oldOrder);
    }

    @Test
    @Transactional
    public void testDelete() {
        Order order = new Order();
        order.setId(1);
        assertTrue(dao.delete(order));
    }

    @Test
    @Transactional
    public void testDeleteById() {
        assertTrue(dao.delete(1));
    }

    //TODO: H2 doesn't support plsql functions. Have to change
    @Disabled
    public void testGetMostFrequentTags() {
        List<Tag> tags = dao.getMostFrequentTags();
        assertTrue(tags.size() > 0);
    }
}