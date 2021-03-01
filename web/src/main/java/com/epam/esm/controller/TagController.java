package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.util.linkbuilders.TagLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Class controller for interacting with {@link TagDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {TagService.class})
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class TagController extends AbstractController<TagService, TagLinkBuilder, TagDTO> {
    private TagService service;

    private TagLinkBuilder linkBuilder;

    @Override
    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @Override
    @Autowired
    public void setLinkBuilder(TagLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    /**
     * Creates {@link TagDTO} object. Returns location and status.
     *
     * @param dto the {@link TagDTO} object.
     * @return the {@link RepresentationModel} object with {@link TagDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<TagDTO> createTag(@Validated(value = PostValidation.class) @RequestBody TagDTO dto) {
        TagDTO createdTag = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(TagController.class).slash(createdTag.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        linkBuilder.buildLink(createdTag);
        return new ResponseEntity<>(createdTag, headers, HttpStatus.OK);
    }

    /**
     * Gets {@link TagDTO} object by id.
     *
     * @param tagId the {@link TagDTO} object id
     * @return the {@link TagDTO} object
     */
    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<TagDTO> readTag(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int tagId) {
        TagDTO tag = service.read(tagId);
        linkBuilder.buildLink(tag);
        return tag;
    }

    /**
     * Gets all {@link TagDTO} objects by parameters.
     *
     * @param name           the name
     * @param page           the page
     * @param size           the size
     * @return the list of {@link TagDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readTagsByParams(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<TagDTO> tags;
        CollectionModel result;
        SearchCriteria searchCriteria = new SearchCriteria(null, name, null, null);
        tags = service.readByParams(searchCriteria, page, size);
        linkBuilder.buildLinks(tags);
        result = CollectionModel.of(tags);
        if (Stream.of(page, size).noneMatch(Objects::isNull)) {
            int lastPage = service.getLastPage(searchCriteria, size);
            if (hasPrevious(page)) {
                linkBuilder.buildPreviousPageLink(result, name, page, size);
            }
            if (hasNext(page, lastPage)) {
                linkBuilder.buildNextPageLink(result, name, page, size);
            }
        }
        return result;
    }

    /**
     * Deletes {@link TagDTO} object.
     *
     * @param dto the {@link TagDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteTag(@Validated(value = DeleteValidation.class) @RequestBody TagDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes {@link TagDTO} object.
     *
     * @param tagId the id of {@link TagDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(value = "/{tagId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTagByUrlId(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int tagId) {
        if (service.delete(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean hasNext(int page, int lastPage) {
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
