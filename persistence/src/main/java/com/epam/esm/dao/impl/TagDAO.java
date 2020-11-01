package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.ErrorCodesManager;
import com.epam.esm.exception.NoTagException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@ComponentScan(basePackageClasses = HikariCPDataSource.class)
public class TagDAO implements AbstractDAO<Tag> {

    private final static String SQL_INSERT_TAG = "INSERT INTO esm_module2.tags (name) VALUES (?)";

    private final static String SQL_READ_TAG = "SELECT id, name FROM esm_module2.tags WHERE id = (?)";

    private final static String SQL_READ_TAG_BY_NAME = "SELECT id, name FROM esm_module2.tags WHERE name = (?)";

    private final static String SQL_READ_ALL = "SELECT id, name FROM esm_module2.tags";

    private final static String SQL_DELETE_TAG = "DELETE FROM esm_module2.tags WHERE name = (?)";

    private final static Logger LOGGER = LogManager.getLogger(TagDAO.class);

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Tag create(Tag tag) throws DAOException {
        KeyHolder key = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getName());
                return ps;
                }, key);
        } catch (DuplicateKeyException ex) {
            LOGGER.log(Level.ERROR, String.format("Tag with name = {%s} already exists.", tag.getName()), ex);
            throw new DuplicateTagException(String.format("Tag with name = {%s} already exists.", tag.getName()), ex,
                    tag.getName(), ErrorCodesManager.DUPLICATE_TAG);
        }
        if (key.getKeys() != null) {
            tag.setId((int)key.getKeys().get("id"));
            return tag;
        } else {
            LOGGER.log(Level.ERROR, String.format("Cannot create tag with name = {%s}.", tag.getName()));
            throw new DAOException(String.format("Cannot create tag with name = {%s}.", tag.getName()),
                    tag.getName(), ErrorCodesManager.TAG_DOESNT_EXIST);
        }
    }

    @Override
    public Tag read(int id) throws DAOException {
        try {
            return read(id, SQL_READ_TAG);
        } catch (EmptyResultDataAccessException|DAOException ex) {
            LOGGER.log(Level.ERROR, String.format("Tag with id = {%s} doesn't exist.", String.valueOf(id)), ex);
            throw new NoTagException(String.format("Tag with id = {%s} doesn't exist.", String.valueOf(id)), ex,
                    String.valueOf(id), ErrorCodesManager.TAG_DOESNT_EXIST);
        }
    }

    public Tag read(String name) throws DAOException{
        try {
            return read(name, SQL_READ_TAG_BY_NAME);
        } catch (EmptyResultDataAccessException|DAOException ex) {
            LOGGER.log(Level.ERROR, String.format("Tag with id = {%s} doesn't exist.", name), ex);
            throw new NoTagException(String.format("Tag with id = {%s} doesn't exist.", name), ex,
                    name, ErrorCodesManager.TAG_DOESNT_EXIST);
        }
    }

    private Tag read(Object param, String query) {
        Tag tag;
        Object[] params = new Object[] {param};

        RowMapper<Tag> rowMapper = (rs, rowNum) -> {
            Tag row = new Tag();
            row.setId(rs.getInt("id"));
            row.setName(rs.getString("name"));
            return row;
        };
        tag = template.queryForObject(query, params, rowMapper);
        if (tag == null) {
            throw new DAOException("Tag doesn't exist.", ErrorCodesManager.TAG_DOESNT_EXIST);
        } else {
            return tag;
        }
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    public boolean delete(Tag tag) {
        Object[] params = new Object[] {tag.getName()};
        int[] types = new int[] {Types.VARCHAR};
        return template.update(SQL_DELETE_TAG, params, types) > 0;
    }

    @Override
    public List<Tag> readAll() {
        List<Tag> tags = new ArrayList<>();

        RowMapper<List<Tag>> rowMapper = (rs, rowNum) -> {
            List<Tag> rows = new ArrayList<>();
            do {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setName(rs.getString("name"));
                rows.add(tag);
            } while (rs.next());
            return rows;
        };

        try {
            tags = template.queryForObject(SQL_READ_ALL, rowMapper);
            return tags;
        } catch (EmptyResultDataAccessException e) {
            return tags;
        }
    }
}
