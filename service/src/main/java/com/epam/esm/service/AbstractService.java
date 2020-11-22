package com.epam.esm.service;

import java.util.List;

/**
 * The interface for interacting with service module.
 *
 * @param <U> the type parameter
 */
public interface AbstractService<U> {
    /**
     * Creates entity. Returns {@link U} object if entity in database was created.
     *
     * @param entity the object of {@link U}
     * @return the object of {@link U}
     */
    U create(U entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the object of {@link U}
     */
    U read(int id);

    /**
     * Updates entity. Returns {@link U} object if entity in database was updated.
     *
     * @param entity the object of {@link U}
     * @return the t
     */
    U update(U entity);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param dto the object of {@link U}
     * @return {@code true} if entity was deleted
     */
    boolean delete(U dto);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param id the id of {@link U} object
     * @return {@code true} if entity was deleted
     */
    boolean delete(int id);

    /**
     * Reads all {@link U} objects.
     *
     * @return the list of {@link U} object
     */
    List<U> readAll();
}
