package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.linkbuilders.LinkBuilder;
import org.springframework.hateoas.CollectionModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * The abstract class for controllers.
 *
 * @param <T> the {@link AbstractService} type
 * @param <U> the {@link LinkBuilder} type
 * @param <Z> the type for collection of returning objects.
 */
public abstract class AbstractController<T extends AbstractService, U extends LinkBuilder, Z> {

    protected T service;

    protected U linkBuilder;

    public T getService() {
        return service;
    }

    public void setService(T service) {
        this.service = service;
    }

    public U getLinkBuilder() {
        return linkBuilder;
    }

    public void setLinkBuilder(U linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    /**
     * Gets the objects for one page.
     *
     * @param page the number of page
     * @param size the size of page
     * @return the collection model
     */
    public CollectionModel readPaginatedForController(Integer page, Integer size) {
        List<Z> objects;
        CollectionModel result;
        if (Stream.of(page, size).allMatch(Objects::isNull)) {
            objects = service.readAll();
            linkBuilder.buildLinks(objects);
            result = CollectionModel.of(objects);
        } else {
            int lastPage = service.getLastPage(size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            objects = service.readPaginated(page, size);
            linkBuilder.buildLinks(objects);
            result = CollectionModel.of(objects);

            if (hasPrevious(page)) {
                linkBuilder.buildPreviousPageLink(result, page, size);
            }
            if (hasNext(service, page, size)) {
                linkBuilder.buildNextPageLink(result, page, size);
            }
        }
        return result;
    }

    private boolean hasNext(AbstractService service, int page, int size) {
        int lastPage = service.getLastPage(size);
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
