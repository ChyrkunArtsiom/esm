package com.epam.esm.service;

import com.epam.esm.util.SearchCriteria;

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
     * Reads all {@link U} objects.
     *
     * @return the list of {@link U} object
     */
    List<U> readAll();

    /**
     * Gets the list of {@link U} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param criteria the {@link SearchCriteria} object
     * @return the list of {@link U} objects
     */
    List<U> readByParams(SearchCriteria criteria);

    /**
     * Gets the list of {@link U} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link U} objects
     */
    List<U> readPaginated(int page, int size);

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    int getLastPage(int size);
}
