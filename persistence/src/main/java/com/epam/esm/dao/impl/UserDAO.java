package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateUserException;
import com.epam.esm.exception.NoUserException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * Class for interacting with{@link User} table in databse. Implements {@link AbstractDAO}.
 */
@Repository
@EnableAutoConfiguration
@EntityScan(basePackageClasses = User.class)
public class UserDAO implements AbstractDAO<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User user) {
        try{
            entityManager.persist(user);
            entityManager.flush();
            return user;
        } catch (PersistenceException ex) {
            throw new DuplicateUserException(
                    String.format("User with name = {%s} already exists.", user.getName()), ex,
                    user.getName());
        }
    }

    @Override
    public User read(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new NoUserException(String.format("User with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id));
        } else {
            return user;
        }
    }

    @Override
    public User read(String name) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM users u WHERE u.name=:name", User.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoUserException(String.format("User with name = {%s} doesn't exist.", name), ex,
                    name);
        }
    }

    @Override
    public List<User> readAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM users u ORDER BY u.id", User.class);
        return query.getResultList();
    }

    /**
     * Gets the list of {@link User} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link User} objects
     */
    public List<User> readPaginated(Integer page, Integer size) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM users u ORDER BY u.id", User.class);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException("User is not supported by update method.");
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(Integer size) {
        Query query = entityManager.createQuery("SELECT count(u.id) FROM users u");
        Long count = (Long)query.getSingleResult();
        int pages = count.intValue()/size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
