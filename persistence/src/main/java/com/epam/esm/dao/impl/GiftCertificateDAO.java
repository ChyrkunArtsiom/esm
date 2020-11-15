package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.ErrorCodesManager;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.util.SortOrder;
import com.epam.esm.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for interacting with{@link GiftCertificate} table in database. Implements {@link AbstractDAO}.
 */
@Repository
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = TagDAO.class)
@EntityScan(basePackageClasses = GiftCertificate.class)
public class GiftCertificateDAO implements AbstractDAO<GiftCertificate> {

    private final static String GET_TAGS_BY_CERTIFICATE_SQL = "SELECT name FROM esm_module2.tags " +
            "INNER JOIN esm_module2.certificate_tag ON tags.id = certificate_tag.tag_id WHERE certificate_id = (?)";

    private final static String GET_CERTIFICATES_BY_PARAMS_WITH_TAGS_SQL =
            "SELECT DISTINCT certificates.id, certificates.name, " +
            "description, price, create_date, last_update_date, duration " +
            "FROM esm_module2.certificates INNER JOIN esm_module2.certificate_tag " +
            "ON certificates.id = certificate_tag.certificate_id INNER JOIN esm_module2.tags " +
            "ON certificate_tag.tag_id = tags.id";

    private final static String GET_CERTIFICATES_BY_PARAMS_WITHOUT_TAGS_SQL =
            "SELECT DISTINCT certificates.id, certificates.name, " +
            "description, price, create_date, last_update_date, duration " +
            "FROM esm_module2.certificates";

    @Autowired
    private JdbcTemplate template;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        try{
            OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
            giftCertificate.setCreateDate(currentTime);
            entityManager.persist(giftCertificate);
            entityManager.flush();
            return giftCertificate;
        } catch (PersistenceException ex) {
            throw new DuplicateCertificateException(
                    String.format("Certificate with name = {%s} already exists.", giftCertificate.getName()), ex,
                    giftCertificate.getName(), ErrorCodesManager.DUPLICATE_CERTIFICATE);
        }
    }

    @Override
    public GiftCertificate read(int id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        if (certificate == null) {
            throw new NoCertificateException(
                    String.format("Certificate with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        } else {
            return certificate;
        }
    }

    @Override
    public GiftCertificate read(String name) {
        try {
            TypedQuery<GiftCertificate> query = entityManager.createQuery(
                    "SELECT с FROM certificates с WHERE с.name=:name", GiftCertificate.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoCertificateException(
                    String.format("Certificate with name = {%s} doesn't exist.", String.valueOf(name)), ex,
                    String.valueOf(name), ErrorCodesManager.CERTIFICATE_DOESNT_EXIST);
        }
    }

    @Override
    public List<GiftCertificate> readAll() {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                "SELECT c FROM certificates c", GiftCertificate.class);
        return query.getResultList();
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
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
        giftCertificate.setLastUpdateDate(currentTime);
        GiftCertificate updatedCertificate = entityManager.merge(giftCertificate);
        entityManager.flush();
        return updatedCertificate;
    }

    @Override
    public boolean delete(GiftCertificate certificate) {
        try {
            TypedQuery<GiftCertificate> query = entityManager.createQuery(
                    "SELECT c FROM certificates c WHERE c.name=:name", GiftCertificate.class);
            query.setParameter("name", certificate.getName());
            GiftCertificate toDelete = query.getSingleResult();
            entityManager.remove(toDelete);
            return true;
        } catch (NoResultException | IllegalArgumentException e) {
            return false;
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

    private Set<Tag> readTagsByCertificateId(int id) {
        Set<Tag> tags = new HashSet<>();
        Object[] params = new Object[] {id};
        try {
            tags = template.query(GET_TAGS_BY_CERTIFICATE_SQL, params, getResultSetExtractor());
            return tags;
        } catch (EmptyResultDataAccessException e) {
            return tags;
        }
    }

    private String generateQuery(SearchCriteria criteria) {
        StringBuilder query = new StringBuilder();
        boolean hasWhere = false;
        if (!criteria.getTagName().isEmpty()) {
            query.append(GET_CERTIFICATES_BY_PARAMS_WITH_TAGS_SQL);
            query.append(" WHERE ");
            query.append("tags.name = (?)");
            hasWhere = true;
        } else {
            query.append(GET_CERTIFICATES_BY_PARAMS_WITHOUT_TAGS_SQL);
        }
        if (!criteria.getName().isEmpty()) {
            if (!hasWhere) {
                query.append(" WHERE ");
                query.append("certificates.name LIKE (?)");
                hasWhere = true;
            } else {
                query.append("AND certificates.name LIKE (?)");
            }
        }
        if (!criteria.getDescription().isEmpty()) {
            if (!hasWhere) {
                query.append(" WHERE ");
                query.append("description LIKE (?)");
            } else {
                query.append("AND description LIKE (?)");
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

    private ResultSetExtractor<Set<Tag>> getResultSetExtractor() {
        return resultSet -> {
            Set<Tag> tags = new HashSet<>();
            while (resultSet.next()) {
                Tag tag = new Tag(/*resultSet.getInt("id"),*/ resultSet.getString("name"));
                tags.add(tag);
            }
            return tags;
        };
    }

    @Override
    public List<GiftCertificate> readPaginated(int page, int size) {
        return null;
    }

    @Override
    public int getLastPage(int size) {
        return 0;
    }
}
