package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.datasource.HikariCPDataSource;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ExceptionType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ComponentScan(basePackageClasses = HikariCPDataSource.class)
public class GiftCertificateDAO implements AbstractDAO<GiftCertificate> {

    private final static String SQL_INSERT_CERTIFICATE = "INSERT INTO esm_module2.certificates (name, description," +
            "price, create_date, duration) VALUES (?,?,?,?,?)";

    private final static String SQL_READ_CERTIFICATE = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates " +
            "WHERE id = (?)";

    private final static String SQL_READ_ALL = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates";

    private final static String SQL_UPDATE_CERTIFICATE = "UPDATE esm_module2.certificates SET name = (?)," +
            "description = (?), price = (?), last_update_date = (?), duration = (?) WHERE id = (?)";

    private final static String SQL_DELETE_CERTIFICATE = "DELETE FROM esm_module2.certificates WHERE name = (?)";

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder key = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setDouble(3, giftCertificate.getPrice());
                //Setting up current datetime
                ps.setTimestamp(4, Timestamp.valueOf(
                        LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
                ps.setInt(5, giftCertificate.getDuration());
                connection.commit();
                return ps;
            }, key);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        if (key.getKeys() != null) {
            giftCertificate.setId((int)key.getKeys().get("id"));
            return giftCertificate;
        } else {
            return null;
        }
    }

    @Override
    public Optional<GiftCertificate> read(int id) throws DAOException{
        GiftCertificate certificate;
        Object[] params = new Object[] {id};
        RowMapper<GiftCertificate> rowMapper = (rs, rowNum) -> certificateMapper(rs);

        try {
            certificate = template.queryForObject(SQL_READ_CERTIFICATE, params, rowMapper);
            if (certificate == null) {
                return Optional.empty();
            } else {
                return Optional.of(certificate);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DAOException(ExceptionType.CERTIFICATE_DOESNT_EXIST);
        }
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> oldCertificate = read(giftCertificate.getId());
        if (oldCertificate.isPresent()) {
            Object[] params = new Object[] {giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), OffsetDateTime.now(), giftCertificate.getDuration(),
                    giftCertificate.getId()};
            int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.TIMESTAMP_WITH_TIMEZONE,
                    Types.INTEGER, Types.INTEGER};

            if (template.update(SQL_UPDATE_CERTIFICATE, params, types) > 0) {
                return oldCertificate;
            }
        }
        return Optional.empty();
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
                GiftCertificate certificate = certificateMapper(rs);
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

    private GiftCertificate certificateMapper(ResultSet rs) throws SQLException {
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
}
