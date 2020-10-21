package com.epam.esm.dao.impl;

import com.epam.esm.datasource.BasicDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CertificateDAOTest {

    @Test
    void getCount() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BasicDataSourceConfig.class);
        ctx.scan("com.epam.esm");
        ctx.refresh();

        CertificateDAO dao = ctx.getBean(CertificateDAO.class);
        int count = dao.getCount();

        assertEquals(2, count);

    }
}