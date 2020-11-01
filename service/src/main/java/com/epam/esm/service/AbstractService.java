package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.util.SearchCriteria;

import java.util.List;

/**
 * The interface for interacting with service module.
 *
 * @param <U> the {@link AbstractDTO} type
 */
public interface AbstractService<U extends AbstractDTO> {
    /**
     * Creates entity. Returns {@link AbstractDTO} object if entity in database was created.
     *
     * @param entity the object of {@link AbstractDTO}
     * @return the object of {@link AbstractDTO}
     */
    U create(U entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the object of {@link AbstractDTO}
     */
    U read(int id);

    /**
     * Updates entity. Returns {@link AbstractDTO} object if entity in database was updated.
     *
     * @param entity the object of {@link AbstractDTO}
     * @return the t
     */
    U update(U entity);

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param dto the object of {@link AbstractDTO}
     * @return {@code true} if entity was deleted
     */
    boolean delete(U dto);

    /**
     * Reads all {@link AbstractDTO} objects.
     *
     * @return the list of {@link AbstractDTO} object
     */
    List<U> readAll();

    /**
     * Gets the list of {@link AbstractDTO} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param criteria the {@link SearchCriteria} object
     * @return the list of {@link AbstractDTO} objects
     */
    List<U> readByParams(SearchCriteria criteria);
}
