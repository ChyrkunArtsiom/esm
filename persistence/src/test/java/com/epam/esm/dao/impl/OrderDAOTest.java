package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderDAO.class, UserDAO.class, GiftCertificateDAO.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderDAOTest {

    @Autowired
    private OrderDAO dao;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GiftCertificateDAO certificateDAO;

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
        assertEquals(tags.size(), size);
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
    public void testCreate() {
        User user = userDAO.read(1);
        GiftCertificate certificate = certificateDAO.read(1);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        Order order = new Order( 10.0, date, user, Arrays.asList(certificate));
        assertNotNull(dao.create(order));
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

    //TODO: H2 doesn't support plsql functions. Have to change
    @Disabled
    public void testGetMostFrequentTags() {
        List<Tag> tags = dao.getMostFrequentTags();
        assertTrue(tags.size() > 0);
    }
}