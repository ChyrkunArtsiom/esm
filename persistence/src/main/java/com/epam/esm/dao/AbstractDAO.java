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
     * Reads all {@link T} objects.
     *
     * @return the list of {@link T} object
     */
    List<T> readAll();


    /**
     * Gets the list of {@link T} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link T} objects
     */
    List<T> readPaginated(int page, int size);

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    int getLastPage(int size);
}
