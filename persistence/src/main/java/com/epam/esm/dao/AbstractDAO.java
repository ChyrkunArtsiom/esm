package com.epam.esm.dao;

import java.util.List;

/**
 * The interface for interacting with database.
 *
 * @param <T> the type parameter
 */
public interface AbstractDAO<T> {
    /**
     * Creates entity. Returns {@link T} object if entity in database was created.
     *
     * @param entity the object
     * @return the object of {@link T}
     */
    T create(T entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the object of {@link T}
     */
    T read(int id);

    /**
     * Gets entity by name.
     *
     * @param name the string of name
     * @return the object of {@link T}
     */
    T read(String name);

    /**
     * Updates entity. Returns {@link T} object if entity in database was updated.
     *
     * @param entity the object of {@link T}
     * @return the t
     */
    T update(T entity);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param entity the object of {@link T}
     * @return {@code true} if entity was deleted
     */
    boolean delete(T entity);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param id the id of {@link T} object to delete
     * @return {@code true} if entity was deleted
     */
    boolean delete(int id);

    /**
     * Reads all {@link T} objects.
     *
     * @return the list of {@link T} object
     */
    List<T> readAll();
}
