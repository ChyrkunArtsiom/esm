package com.epam.esm.service;

import java.util.List;

/**
 * The interface for interacting with service module.
 * @param <T> the type for returning objects
 * @param <U> the type for incoming objects
 */
public interface AbstractService<T, U> {
    /**
     * Creates entity. Returns {@link U} object if entity in database was created.
     *
     * @param entity the object of {@link U}
     * @return the object of {@link U}
     */
    T create(U entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the object of {@link U}
     */
    T read(int id);

    /**
     * Updates entity. Returns {@link U} object if entity in database was updated.
     *
     * @param entity the object of {@link U}
     * @return the t
     */
    T update(U entity);

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
    List<T> readAll();

    /**
     * Gets the list of {@link U} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link U} objects
     */
    List<T> readPaginated(Integer page, Integer size);

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    int getLastPage(Integer size);
}
