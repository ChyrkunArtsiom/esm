package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.List;

/**
 * The interface for interacting with database.
 *
 * @param <T> the type parameter
 */
public interface AbstractDAO<T extends AbstractEntity> {
    /**
     * Creates entity. Returns {@link AbstractEntity} object if entity in database was created.
     *
     * @param entity the object of {@link AbstractEntity}
     * @return the object of {@link AbstractEntity}
     */
    T create(T entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the object of {@link AbstractEntity}
     */
    T read(int id);

    /**
     * Updates entity. Returns {@link AbstractEntity} object if entity in database was updated.
     *
     * @param entity the object of {@link AbstractEntity}
     * @return the t
     */
    T update(T entity);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param entity the object of {@link AbstractEntity}
     * @return {@code true} if entity was deleted
     */
    boolean delete(T entity);

    /**
     * Reads all {@link AbstractEntity} objects.
     *
     * @return the list of {@link AbstractEntity} object
     */
    List<T> readAll();
}
