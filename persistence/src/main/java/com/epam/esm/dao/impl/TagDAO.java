package com.epam.esm.dao.impl;

import com.epam.esm.dao.PostgresqlDAO;
import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.Tag;
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
import java.util.Optional;

@Repository
@ComponentScan(basePackageClasses = HikariCPDataSource.class)
public class TagDAO implements PostgresqlDAO<Tag> {

    private final static String SQL_INSERT_TAG = "INSERT INTO esm_module2.tags (name) VALUES (?)";

    private final static String SQL_READ_TAG = "SELECT id, name FROM esm_module2.tags WHERE id = (?)";

    private final static String SQL_READ_TAG_BY_NAME = "SELECT id, name FROM esm_module2.tags WHERE name = (?)";

    private final static String SQL_READ_ALL = "SELECT id, name FROM esm_module2.tags";

    private final static String SQL_DELETE_TAG = "DELETE FROM esm_module2.tags WHERE id = (?)";

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int create(Tag tag) {
        KeyHolder key = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getName());
                return ps;
                }, key);
        } catch (DuplicateKeyException e) {
            //throw new
        }
        if (key.getKeys() != null) {
            return (int)key.getKeys().get("id");
        } else {
            return 0;
        }
    }

    @Override
    public Optional<Tag> read(int id) {
        return readByParam(id, SQL_READ_TAG);
    }

    public Optional<Tag> read(String name) {
        return readByParam(name, SQL_READ_TAG_BY_NAME);
    }

    private Optional<Tag> readByParam(Object param, String query) {
        Tag tag;
        Object[] params = new Object[] {param};

        RowMapper<Tag> rowMapper = (rs, rowNum) -> {
            Tag row = new Tag();
            row.setId(rs.getInt("id"));
            row.setName(rs.getString("name"));
            return row;
        };

        try {
            tag = template.queryForObject(query, params, rowMapper);
            if (tag == null) {
                return Optional.empty();
            } else {
                return Optional.of(tag);
            }
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> update(Tag tag) {
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    public boolean delete(Tag tag) {
        Object[] params = new Object[] {tag.getId()};
        int[] types = new int[] {Types.INTEGER};

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
