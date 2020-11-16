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
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Class for interacting with{@link GiftCertificate} table in database. Implements {@link AbstractDAO}.
 */
@Repository
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = TagDAO.class)
@EntityScan(basePackageClasses = GiftCertificate.class)
public class GiftCertificateDAO implements AbstractDAO<GiftCertificate> {

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
                "SELECT c FROM certificates c ORDER BY c.id", GiftCertificate.class);
        return query.getResultList();
    }

    /**
     * Gets the list of {@link GiftCertificate} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param criteria the {@link SearchCriteria} object
     * @return the list of {@link GiftCertificate} objects
     */
    public List<GiftCertificate> readByParams(SearchCriteria criteria, Integer page, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> tags = root.join("tags");
        In<String> inTags = builder.in(tags.get("name"));
        String[] tagNames = new String[0];
        if (criteria.getTagNames() != null) {
            tagNames = criteria.getTagNames().split(",");
        }

        query.select(root).distinct(true).from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        //WHERE clause for certificate name
        if (criteria.getName() != null) {
            Predicate predicateForName = builder.like(root.get("name"), "%" + criteria.getName() + "%");
            predicates.add(predicateForName);
        }
        //WHERE clause for certificate description
        if (criteria.getDescription() != null) {
            Predicate predicateForDescription =
                    builder.like(root.get("description"), "%" + criteria.getDescription() + "%");
            predicates.add(predicateForDescription);
        }
        //WHERE clause for tag names
        if (criteria.getTagNames() != null) {
            for (String tagName : tagNames) {
                inTags.value(tagName);
            }
            predicates.add(inTags);
        }
        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);
        query.where(predArray);
        Order order = getOrder(builder, root, criteria.getSort());
        query.orderBy(order);

        //GROUP BY and HAVING
        if (criteria.getTagNames() != null) {
            query.groupBy(root.get("id"));
            query.having(builder.equal(builder.count(root.get("id")), tagNames.length));
        }

        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(query);
        if (page != null) {
            typedQuery.setFirstResult((page - 1) * size);
        }
        if (size != null) {
            typedQuery.setMaxResults(size);
        }
        return typedQuery.getResultList();
    }

    private Order getOrder(CriteriaBuilder builder, Root root, String sort) {
        String[] typeOrder = sort.split("_");
        SortType sortType = SortType.valueOf(typeOrder[0].toUpperCase());
        SortOrder sortOrder = SortOrder.valueOf(typeOrder[1].toUpperCase());
        Expression expression = null;
        Order order = null;

        switch (sortType) {
            case NAME: {
                expression = root.get("name");
                break;
            }
            case DATE: {
                expression = root.get("create_date");
                break;
            }
        }
        switch (sortOrder) {
            case ASC: {
                order = builder.asc(expression);
                break;
            }
            case DESC: {
                order = builder.desc(expression);
                break;
            }
        }
        return order;
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


    public List<GiftCertificate> readPaginated(int page, int size) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                "SELECT c FROM certificates c ORDER BY c.id", GiftCertificate.class);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(int size) {
        Query query = entityManager.createQuery("SELECT count(c.id) FROM certificates c");
        Long count = (Long)query.getSingleResult();
        int pages = count.intValue()/size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
