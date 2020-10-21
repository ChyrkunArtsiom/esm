package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateDAO implements AbstractDAO {

    private final static String SQL_SELECT_COUNT = "SELECT COUNT(*) FROM esm_module2.certificates";

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int getCount() {
        return template.queryForObject(SQL_SELECT_COUNT, Integer.class);
    }
}
