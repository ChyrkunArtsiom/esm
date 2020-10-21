package com.epam.esm.dao.impl;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {

    @Test
    void getCount() throws SQLException {
        TagDAO dao = new TagDAO();
        int count = dao.getCount();
        assertEquals(3, count);
    }
}