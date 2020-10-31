package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
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

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private final static String SQL_READ_TAGS_BY_CERTIFICATE = "SELECT name FROM esm_module2.tags " +
            "INNER JOIN esm_module2.certificate_tag ON tags.id = certificate_tag.tag_id WHERE certificate_id = (?)";

    private final static String SQL_READ_ALL = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates";

    private final static String SQL_UPDATE_CERTIFICATE = "UPDATE esm_module2.certificates SET name = (?)," +
            "description = (?), price = (?), last_update_date = (?), duration = (?) WHERE id = (?)";

    private final static String SQL_DELETE_CERTIFICATE = "DELETE FROM esm_module2.certificates WHERE name = (?)";

    private final static Logger LOGGER = LogManager.getLogger(GiftCertificateDAO.class);

    private JdbcTemplate template;
    private TagDAO tagDAO;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

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
            bindTagsWithCertificate(giftCertificate);
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
        GiftCertificate certificate;
        Object[] params = new Object[] {id};
        RowMapper<GiftCertificate> rowMapper = (rs, rowNum) -> mapToCertificate(rs);

        try {
            certificate = template.queryForObject(SQL_READ_CERTIFICATE, params, rowMapper);
            if (certificate == null) {
                throw new DAOException("Certificate doesn't exist.", ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
            } else {
                List<String> tags = readTagsByCertificateId(id);
                certificate.setTags(tags);
                return certificate;
            }
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.log(Level.ERROR, String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(id)), ex);
            throw new NoCertificateException(
                    String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(id)), ex,
                    String.valueOf(id), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate oldCertificate = read(giftCertificate.getId());
        if (oldCertificate != null) {
            Object[] params = new Object[] {giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), OffsetDateTime.now(), giftCertificate.getDuration(),
                    giftCertificate.getId()};
            int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.TIMESTAMP_WITH_TIMEZONE,
                    Types.INTEGER, Types.INTEGER};

            if (template.update(SQL_UPDATE_CERTIFICATE, params, types) > 0) {
                return oldCertificate;
            }
        }
        return null; //Throw an exception
    }

    @Override
    public boolean delete(GiftCertificate certificate) {
        Object[] params = new Object[] {certificate.getName()};
        int[] types = new int[] {Types.VARCHAR};
        return template.update(SQL_DELETE_CERTIFICATE, params, types) > 0;
    }

    @Override
    public List<GiftCertificate> readAll() {
        List<GiftCertificate> certificates = new ArrayList<>();

        RowMapper<List<GiftCertificate>> rowMapper = (rs, rowNum) -> {
            List<GiftCertificate> rows = new ArrayList<>();
            do {
                GiftCertificate certificate = mapToCertificate(rs);
                certificate.setTags(readTagsByCertificateId(certificate.getId()));
                rows.add(certificate);
            } while (rs.next());
            return rows;
        };

        try {
            certificates = template.queryForObject(SQL_READ_ALL, rowMapper);
            return certificates;
        } catch (EmptyResultDataAccessException e) {
            return certificates;
        }
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
        RowMapper<List<String>> tagsRowMapper = (rs, rowNum) -> {
            List<String> rows = new ArrayList<>();
            do {
                String tag = rs.getString("name");
                rows.add(tag);
            } while (rs.next());
            return rows;
        };
        try {
            tags = template.queryForObject(SQL_READ_TAGS_BY_CERTIFICATE, params, tagsRowMapper);
            return tags;
        } catch (EmptyResultDataAccessException e) {
            return tags;
        }
    }

    private void bindTagsWithCertificate(GiftCertificate certificate) {
        for (String tag : certificate.getTags()) {
            Tag selected = tagDAO.read(tag);
            try {
                template.update(SQL_INSERT_CERTIFICATE_TAG, certificate.getId(), selected.getId());
            }catch (DuplicateKeyException ex) {
                LOGGER.log(Level.ERROR, String.format("Certificate is already bonded with tag id = {%s}.",
                        selected.getId()), ex);
                throw new DuplicateCertificateTagException(
                        String.format("Certificate is already bonded with tag id = {%s}.",
                        selected.getId()), ex, selected.getName(), ErrorCodesManager.DUPLICATE_CERTIFICATE_TAG);
            }

        }
    }
}
