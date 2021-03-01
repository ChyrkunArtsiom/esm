package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.util.SearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Class for interacting with{@link Tag} table in databse. Implements {@link AbstractDAO}.
 */
@Repository
public class TagDAO implements AbstractDAO<Tag> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        try{
            entityManager.persist(tag);
            entityManager.flush();
            return tag;
        } catch (PersistenceException ex) {
            throw new DuplicateTagException(String.format("Tag with name = {%s} already exists.", tag.getName()), ex,
                    tag.getName());
        }
    }

    @Override
    public Tag read(int id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new NoTagException(String.format("Tag with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id));
        } else {
            return tag;
        }
    }

    @Override
    public Tag read(String name) {
        try {
            TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM tags t WHERE t.name=:name", Tag.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoTagException(String.format("Tag with name = {%s} doesn't exist.", name), ex,
                    name);
        }
    }

    @Override
    public List<Tag> readAll() {
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM tags t ORDER BY t.id", Tag.class);
        return query.getResultList();
    }

    /**
     * Gets the list of {@link Tag} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link Tag} objects
     */
    public List<Tag> readPaginated(SearchCriteria searchCriteria, Integer page, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);

        if (searchCriteria.getName() != null) {
            Predicate predicateForName = builder.like(builder.lower(root.get("name")), "%" + searchCriteria.getName().toLowerCase() + "%");
            query.where(predicateForName);
        }

        query.orderBy(builder.asc(root.get("id")));
        //Pagination
        TypedQuery<Tag> typedQuery = entityManager.createQuery(query);
        if (page != null) {
            typedQuery.setFirstResult((page - 1) * size);
        }
        if (size != null) {
            typedQuery.setMaxResults(size);
        }
        return typedQuery.getResultList();
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    public boolean delete(Tag tag) {
        try {
            TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM tags t WHERE t.name=:name", Tag.class);
            query.setParameter("name", tag.getName());
            Tag toDelete = query.getSingleResult();
            entityManager.remove(toDelete);
            return true;
        } catch (NoResultException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            Tag tag = read(id);
            entityManager.remove(tag);
            return true;
        } catch (NoResultException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(SearchCriteria searchCriteria, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //Outer SELECT COUNT query
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        //Inner SELECT query
        Subquery<Tag> subquery = query.subquery(Tag.class);
        Root<Tag> innerRoot = subquery.from(Tag.class);

        subquery.select(innerRoot).distinct(true);
        query.select(builder.count(tagRoot)).where(builder.in(tagRoot).value(subquery));

        if (searchCriteria.getName() != null) {
            Predicate predicateForName = builder.like(builder.lower(innerRoot.get("name")), "%" + searchCriteria.getName().toLowerCase() + "%");
            subquery.where(predicateForName);
        }

        Long count = entityManager.createQuery(query).getSingleResult();
        int pages = count.intValue() / size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
