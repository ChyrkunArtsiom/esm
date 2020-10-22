package com.epam.esm.dao.impl;

import com.epam.esm.dao.PostgresqlDAO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDAO implements PostgresqlDAO<GiftCertificate> {

    private final static String SQL_INSERT_CERTIFICATE = "INSERT INTO esm_module2.certificates (name, description," +
            "price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";

    private final static String SQL_READ_CERTIFICATE = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates " +
            "WHERE id = (?)";

    private final static String SQL_READ_ALL = "SELECT " +
            "id, name, description, price, create_date, last_update_date, duration FROM esm_module2.certificates";

    private final static String SQL_UPDATE_CERTIFICATE = "UPDATE esm_module2.certificates SET name = (?)," +
            "description = (?), price = (?), create_date = (?), last_update_date = (?), duration = (?) WHERE id = (?)";

    private final static String SQL_DELETE_CERTIFICATE = "DELETE FROM esm_module2.certificates WHERE id = (?)";

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public boolean create(GiftCertificate giftCertificate) {
        ZoneId zone = ZoneId.of("Europe/Moscow");
        Object[] params = new Object[] {giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getCreateDate().atZone(zone).toOffsetDateTime(),
                giftCertificate.getLastUpdateDate().atZone(zone).toOffsetDateTime(), giftCertificate.getDuration()};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.TIMESTAMP_WITH_TIMEZONE,
                Types.TIMESTAMP_WITH_TIMEZONE, Types.INTEGER};

        return template.update(SQL_INSERT_CERTIFICATE, params, types) > 0;
    }

    @Override
    public Optional<GiftCertificate> read(int id) {
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
            return Optional.empty();
        }
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> oldCertificate = read(giftCertificate.getId());
        if (oldCertificate.isPresent()) {
            ZoneId zone = ZoneId.of("Europe/Moscow");
            Object[] params = new Object[] {giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getCreateDate().atZone(zone).toOffsetDateTime(),
                    giftCertificate.getLastUpdateDate().atZone(zone).toOffsetDateTime(), giftCertificate.getDuration(),
                    giftCertificate.getId()};
            int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.TIMESTAMP_WITH_TIMEZONE,
                    Types.TIMESTAMP_WITH_TIMEZONE, Types.INTEGER, Types.INTEGER};

            if (template.update(SQL_UPDATE_CERTIFICATE, params, types) > 0) {
                return oldCertificate;
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(GiftCertificate certificate) {
        Object[] params = new Object[] {certificate.getId()};
        int[] types = new int[] {Types.INTEGER};

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
        certificate.setPrice(rs.getFloat("price"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        certificate.setDuration(rs.getInt("duration"));
        return certificate;
    }
}
