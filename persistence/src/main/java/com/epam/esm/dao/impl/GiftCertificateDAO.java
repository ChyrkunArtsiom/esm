package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.util.SortOrder;
import com.epam.esm.util.SortType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with{@link GiftCertificate} table in database. Implements {@link AbstractDAO}.
 */
@Repository
@ComponentScan(basePackageClasses = {HikariCPDataSource.class, TagDAO.class})
public class GiftCertificateDAO implements AbstractDAO<GiftCertificate> {

    private final static String SQL_INSERT_CERTIFICATE = "INSERT INTO esm_module2.certificates (name, description," +
            "price, create_date, duration) VALUES (?,?,?,?,?)";

    private final static String SQL_INSERT_CERTIFICATE_TAG =
            "INSERT INTO esm_module2.certificate_tag (certificate_id, tag_id) VALUES (?, ?)";

    private final static String SQL_READ_CERTIFICATE = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates " +
            "WHERE id = (?)";

    private final static String SQL_READ_CERTIFICATE_BY_NAME = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates " +
            "WHERE name = (?)";

    private final static String SQL_READ_TAGS_BY_CERTIFICATE = "SELECT name FROM esm_module2.tags " +
            "INNER JOIN esm_module2.certificate_tag ON tags.id = certificate_tag.tag_id WHERE certificate_id = (?)";

    private final static String SQL_READ_ALL = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates";

    private final static String SQL_READ_BY_PARAMS_TAGS = "SELECT DISTINCT certificates.id, certificates.name, " +
            "description, price, create_date, last_update_date, duration " +
            "FROM esm_module2.certificates INNER JOIN esm_module2.certificate_tag " +
            "ON certificates.id = certificate_tag.certificate_id INNER JOIN esm_module2.tags " +
            "ON certificate_tag.tag_id = tags.id";

    private final static String SQL_READ_BY_PARAMS_NO_TAGS = "SELECT DISTINCT certificates.id, certificates.name, " +
            "description, price, create_date, last_update_date, duration " +
            "FROM esm_module2.certificates";

    private final static String SQL_UPDATE_CERTIFICATE = "UPDATE esm_module2.certificates SET " +
            "description = (?), price = (?), last_update_date = (?), duration = (?) WHERE name = (?)";

    private final static String SQL_DELETE_CERTIFICATE = "DELETE FROM esm_module2.certificates WHERE name = (?)";

    private final static String SQL_DELETE_CERTIFICATE_TAG =
            "DELETE FROM esm_module2.certificate_tag WHERE certificate_id = (?) AND tag_id = (?)";

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDAO.class);

    private JdbcTemplate template;
    private TagDAO tagDAO;

    /**
     * Sets {@link JdbcTemplate} object.
     *
     * @param template the {@link JdbcTemplate} object
     */
    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Sets {@link TagDAO} object.
     *
     * @param tagDAO the {@link TagDAO} object
     */
    @Autowired
    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) throws DAOException {
        KeyHolder key = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setDouble(3, giftCertificate.getPrice());
                //Setting up current datetime
                OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
                ps.setTimestamp(4, Timestamp.valueOf(
                        LocalDateTime.ofInstant(currentTime.toInstant(), ZoneOffset.UTC)));
                giftCertificate.setCreateDate(currentTime);
                ps.setInt(5, giftCertificate.getDuration());
                return ps;
            }, key);
        } catch (DuplicateKeyException ex) {
            LOGGER.log(Level.ERROR, String.format(
                    "Certificate with name = {%s} already exists.", giftCertificate.getName()), ex);
            throw new DuplicateCertificateException(
                    String.format("Certificate with name = {%s} already exists.", giftCertificate.getName()), ex,
                    giftCertificate.getName(), ErrorCodesManager.DUPLICATE_CERTIFICATE);
        }
        if (key.getKeys() != null) {
            giftCertificate.setId((int)key.getKeys().get("id"));
            bindTagsWithCertificate(giftCertificate.getId(), giftCertificate.getTags());
            return giftCertificate;
        } else {
            LOGGER.log(Level.ERROR,
                    String.format("Cannot create certificate with name = {%s}.", giftCertificate.getName()));
            throw new DAOException(
                    String.format("Cannot create certificate with name = {%s}.", giftCertificate.getName()),
                    giftCertificate.getName(), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        }
    }

    @Override
    public GiftCertificate read(int id) throws NoCertificateException{
        try {
            return read(id, SQL_READ_CERTIFICATE);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.log(Level.ERROR, String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(id)), ex);
            throw new NoCertificateException(
                    String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(id)), ex,
                    String.valueOf(id), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        }
    }

    /**
     * Gets {@link GiftCertificate} object by the name.
     *
     * @param name the name string
     * @return the {@link GiftCertificate} object
     */
    public GiftCertificate read(String name) {
        try {
            return read(name, SQL_READ_CERTIFICATE_BY_NAME);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.log(Level.ERROR, String.format("Certificate with name = {%s} doesn't exist.", String.valueOf(name)), ex);
            throw new NoCertificateException(
                    String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(name)), ex,
                    String.valueOf(name), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        }
    }

    private GiftCertificate read(Object param, String query) {
        GiftCertificate certificate;
        Object[] params = new Object[] {param};
        RowMapper<GiftCertificate> rowMapper = (rs, rowNum) -> mapToCertificate(rs);
        certificate = template.queryForObject(query, params, rowMapper);
        if (certificate == null) {
            throw new DAOException("Certificate doesn't exist.", ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        } else {
            List<String> tags = readTagsByCertificateId(certificate.getId());
            certificate.setTags(tags);
            return certificate;
        }
    }

    @Override
    public List<GiftCertificate> readAll() {
        List<GiftCertificate> certificates = new ArrayList<>();
        RowMapper<List<GiftCertificate>> rowMapper = getRowMapper();

        try {
            certificates = template.queryForObject(SQL_READ_ALL, rowMapper);
            return certificates;
        } catch (EmptyResultDataAccessException e) {
            return certificates;
        }
    }

    /**
     * Gets the list of {@link GiftCertificate} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param criteria the {@link SearchCriteria} object
     * @return the list of {@link GiftCertificate} objects
     */
    public List<GiftCertificate> readByParams(SearchCriteria criteria) {
        List<GiftCertificate> certificates = new ArrayList<>();
        List<String> criteriaTypes = new ArrayList<>();
        if (!criteria.getTagName().isEmpty()) {
            criteriaTypes.add(criteria.getTagName());
        }
        if (!criteria.getName().isEmpty()) {
            criteriaTypes.add("%" + criteria.getName() + "%");
        }
        if (!criteria.getDescription().isEmpty()) {
            criteriaTypes.add("%" + criteria.getDescription() + "%");
        }
        Object[] params = criteriaTypes.toArray();
        RowMapper<List<GiftCertificate>> rowMapper = getRowMapper();

        try {
            certificates = template.queryForObject(generateQuery(criteria), params, rowMapper);
            return certificates;
        } catch (EmptyResultDataAccessException e) {
            return certificates;
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate oldCertificate = read(giftCertificate.getName());
        if (oldCertificate != null) {
            Object[] params = new Object[] {giftCertificate.getDescription(),
                    giftCertificate.getPrice(), OffsetDateTime.now(ZoneOffset.UTC),  giftCertificate.getDuration(), giftCertificate.getName()};
            int[] types = new int[] {Types.VARCHAR, Types.DOUBLE, Types.TIMESTAMP_WITH_TIMEZONE, Types.INTEGER, Types.VARCHAR};
            if (template.update(SQL_UPDATE_CERTIFICATE, params, types) > 0) {
                List<String> oldTags = oldCertificate.getTags();
                List<String> newTags = giftCertificate.getTags();
                List<String> tagsToDelete = getExcessElements(oldTags, newTags);
                List<String> tagsToAdd = getExcessElements(newTags, oldTags);
                deleteBindedTags(tagsToDelete, oldCertificate.getId());
                bindTagsWithCertificate(oldCertificate.getId(), tagsToAdd);
                return oldCertificate;
            }
        }
        LOGGER.log(Level.ERROR,
                String.format("Certificate with name = {%s} doesn't exist.",
                        String.valueOf(giftCertificate.getName())));
        throw new NoCertificateException(
                String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(giftCertificate.getName())),
                String.valueOf(giftCertificate.getName()), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
    }

    @Override
    public boolean delete(GiftCertificate certificate) {
        Object[] params = new Object[] {certificate.getName()};
        int[] types = new int[] {Types.VARCHAR};
        return template.update(SQL_DELETE_CERTIFICATE, params, types) > 0;
    }

    private GiftCertificate mapToCertificate(ResultSet rs) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getInt("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getDouble("price"));
        certificate.setCreateDate(rs.getObject("create_date", OffsetDateTime.class));
        certificate.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
        certificate.setDuration(rs.getInt("duration"));
        return certificate;
    }

    private List<String> readTagsByCertificateId(int id) {
        List<String> tags = new ArrayList<>();
        Object[] params = new Object[] {id};
        try {
            tags = template.query(SQL_READ_TAGS_BY_CERTIFICATE, params, getResultSetExtractor());
            return tags;
        } catch (EmptyResultDataAccessException e) {
            return tags;
        }
    }

    private void bindTagsWithCertificate(int certificateId, List<String> tags) {
        for (String tag : tags) {
            Tag selected = tagDAO.read(tag);
            try {
                template.update(SQL_INSERT_CERTIFICATE_TAG, certificateId, selected.getId());
            }catch (DuplicateKeyException ex) {
                LOGGER.log(Level.ERROR, String.format("Certificate is already bonded with tag id = {%s}.",
                        selected.getId()), ex);
                throw new DuplicateCertificateTagException(
                        String.format("Certificate is already bonded with tag id = {%s}.",
                        selected.getId()), ex, selected.getName(), ErrorCodesManager.DUPLICATE_CERTIFICATE_TAG);
            }

        }
    }

    private void deleteBindedTags(List<String> tags, int certificateId) {
        for (String tag : tags) {
            Tag deleted = tagDAO.read(tag);
            template.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, deleted.getId());
        }
    }

    private List<String> getExcessElements(List<String> firstList, List<String> secondList) {
        List<String> excessElements = new ArrayList<>();
        for (String el : firstList) {
            if (!secondList.contains(el)) {
                excessElements.add(el);
            }
        }
        return excessElements;
    }

    private String generateQuery(SearchCriteria criteria) {
        StringBuilder query = new StringBuilder();
        boolean hasWhere = false;
        if (!criteria.getTagName().isEmpty()) {
            query.append(SQL_READ_BY_PARAMS_TAGS);
            query.append(" WHERE ");
            query.append("tags.name = (?)");
            hasWhere = true;
        } else {
            query.append(SQL_READ_BY_PARAMS_NO_TAGS);
        }
        if (!criteria.getName().isEmpty()) {
            if (!hasWhere) {
                query.append(" WHERE ");
                query.append("certificates.name LIKE (?)");
                hasWhere = true;
            } else {
                query.append(", certificates.name LIKE (?)");
            }
        }
        if (!criteria.getDescription().isEmpty()) {
            if (!hasWhere) {
                query.append(" WHERE ");
                query.append("description LIKE (?)");
            } else {
                query.append(", description LIKE (?)");
            }
        }
        if (!criteria.getSort().isEmpty()) {
            String[] sort = criteria.getSort().split("_");
            query.append(" ORDER BY ");
            SortType sortType = SortType.valueOf(sort[0].toUpperCase());
            SortOrder order = SortOrder.valueOf(sort[1].toUpperCase());
            switch (sortType) {
                case NAME: {
                    query.append("certificates.name");
                    break;
                }
                case DATE: {
                    query.append("create_date");
                    break;
                }
            }
            query.append(" ").append(order);
        }
        return query.toString();
    }

    private RowMapper<List<GiftCertificate>> getRowMapper() {
        return (rs, rowNum) -> {
            List<GiftCertificate> rows = new ArrayList<>();
            do {
                GiftCertificate certificate = mapToCertificate(rs);
                certificate.setTags(readTagsByCertificateId(certificate.getId()));
                rows.add(certificate);
            } while (rs.next());
            return rows;
        };
    }

    private ResultSetExtractor<List<String>> getResultSetExtractor() {
        return resultSet -> {
            List<String> rows = new ArrayList<>();
            while (resultSet.next()) {
                String tag = resultSet.getString("name");
                rows.add(tag);
            }
            return rows;
        };
    }
}
