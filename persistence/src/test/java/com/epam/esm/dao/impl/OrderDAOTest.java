package com.epam.esm.dao.impl;

import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderDAOTest {

    @Autowired
    private OrderDAO dao;

    @Test
    public void testRead() {
        Order order = dao.read(1);
        assertNotNull(order);
    }

    @Test
    @Transactional(readOnly = true)
    public void testReadAll() {
        List<Order> certificates = dao.readAll();
        assertEquals(certificates.size(), 2);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 1;
        List<Order> tags = dao.readPaginated(page, size);
        assertEquals(tags.size(), size);
    }
}