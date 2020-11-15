package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.ErrorCodesManager;
import com.epam.esm.exception.NoTagException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * Class for interacting with{@link Tag} table in databse. Implements {@link AbstractDAO}.
 */
@Repository
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Tag.class)
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
                    tag.getName(), ErrorCodesManager.DUPLICATE_TAG);
        }
    }

    @Override
    public Tag read(int id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new NoTagException(String.format("Tag with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id), ErrorCodesManager.TAG_DOESNT_EXIST);
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
            throw new NoTagException(String.format("Tag with id = {%s} doesn't exist.", name), ex,
                    name, ErrorCodesManager.TAG_DOESNT_EXIST);
        }
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
    public List<Tag> readAll() {
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM tags t", Tag.class);
        return query.getResultList();
    }

    @Override
    public List<Tag> readPaginated(int page, int size) {
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM tags t ORDER BY t.id", Tag.class);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public int getLastPage(int size) {
        Query query = entityManager.createQuery("SELECT count(t.id) FROM tags t");
        Long count = (Long)query.getSingleResult();
        int pages = count.intValue()/size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
