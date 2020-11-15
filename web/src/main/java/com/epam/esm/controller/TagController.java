package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.TagService;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class controller for interacting with {@link TagDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {TagService.class, EsmExceptionHandler.class})
@RequestMapping("/tags")
@Validated
public class TagController {

    private static final String TAGS_PATH = "/tags/";

    private AbstractService<TagDTO> service;

    /**
     * Sets {@link AbstractService} object.
     *
     * @param service the {@link AbstractService} object
     */
    @Autowired
    public void setService(AbstractService<TagDTO> service) {
        this.service = service;
    }

    /**
     * Creates {@link TagDTO} object. Returns location and status.
     *
     * @param dto the {@link TagDTO} object.
     * @return the {@link RepresentationModel} object with {@link TagDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO dto) {
        TagDTO createdTag = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(TagController.class).slash(createdTag.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        createdTag.add(selfLink);
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
    public RepresentationModel readTag(@PathVariable @Positive @Digits(integer = 4, fraction = 0) int tagId) {
        TagDTO tag = service.read(tagId);
        Link deleteLink = linkTo(methodOn(TagController.class).deleteTag(tag)).withRel("delete");
        Link selflink = linkTo(TagController.class).slash(tag.getId()).withSelfRel();
        tag.add(deleteLink, selflink);
        return tag;
    }

    /**
     * Deletes {@link TagDTO} object.
     *
     * @param dto the {@link TagDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteTag(@Valid @RequestBody TagDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all {@link TagDTO} objects.
     *
     * @return the list of {@link TagDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAllTags(
            @RequestParam(value = "page", defaultValue = "1") @Positive @Digits(integer = 4, fraction = 0) int page,
            @RequestParam(value = "size", defaultValue = "2") @Positive @Digits(integer = 4, fraction = 0) int size
    ) {
        int lastPage = service.getLastPage(size);
        if (page > lastPage) {
            throw new ResourceNotFoundException();
        }
        List<TagDTO> tags = service.readPaginated(page, size);
        tags = tags.stream()
                .map(t -> t
                        .add(linkTo(TagController.class).slash(t.getId()).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel result = CollectionModel.of(tags);
        if (hasPrevious(page)) {
            result.add(buildLinkToPage(page - 1, size, "prev"));
        }
        if (hasNext(page, size)) {
            result.add(buildLinkToPage(page + 1, size, "next"));
        }
        return result;
    }

    private Link buildLinkToPage(int page, int size, String rel) {
        return linkTo(methodOn(TagController.class).readAllTags(page, size)).withRel(rel);
    }

    private boolean hasNext(int page, int size) {
        int lastPage = service.getLastPage(size);
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
