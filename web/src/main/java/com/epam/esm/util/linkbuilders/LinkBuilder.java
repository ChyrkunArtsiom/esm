package com.epam.esm.util.linkbuilders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;

/**
 * The interface for building links for objects.
 *
 * @param <T> the type of object
 */
public interface LinkBuilder<T extends RepresentationModel> {
    /**
     * Builds and adds a link to object.
     *
     * @param target the target
     */
    void buildLink(T target);

    /**
     * Builds and adds a link to list of objects.
     *
     * @param targets the targets
     */
    void buildLinks(Collection<T> targets);

    /**
     * Builds a link for previous page.
     *
     * @param target the target
     * @param page   the page
     * @param size   the size
     */
    void buildPreviousPageLink(CollectionModel target, int page, int size);

    /**
     * Builds a link for next page.
     *
     * @param target the target
     * @param page   the page
     * @param size   the size
     */
    void buildNextPageLink(CollectionModel target, int page, int size);

    /**
     * Builds a link for the last page.
     *
     * @param target the target
     * @param page   the page
     * @param size   the size
     */
    void buildLastPageLink(CollectionModel target, int lastPage, int size);
}
