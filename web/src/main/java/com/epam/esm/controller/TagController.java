package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.GetParamIsNotPresent;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
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

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private TagService service;

    /**
     * Sets {@link TagService} object.
     *
     * @param service the {@link TagService} object
     */
    @Autowired
    public void setService(TagService service) {
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
    public RepresentationModel<TagDTO> readTag(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int tagId) {
        TagDTO tag = service.read(tagId);
        Link selfLink = linkTo(TagController.class).slash(tag.getId()).withSelfRel();
        tag.add(selfLink);
        return tag;
    }

    /**
     * Gets all {@link TagDTO} objects.
     *
     * @return the list of {@link TagDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAllTags(
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<TagDTO> tags;
        CollectionModel result;
        if (Stream.of(page, size).allMatch(Objects::isNull)) {
            tags = service.readAll();
            tags = buildSelfLinks(tags);
            result = CollectionModel.of(tags);
        } else if (Stream.of(page, size).anyMatch(Objects::isNull)) {
            throw new GetParamIsNotPresent();
        } else {
            int lastPage = service.getLastPage(size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            tags = service.readPaginated(page, size);
            tags = buildSelfLinks(tags);
            result = CollectionModel.of(tags);

            if (hasPrevious(page)) {
                result.add(linkTo(methodOn(TagController.class).readAllTags(page - 1, size)).withRel("prev"));
            }
            if (hasNext(page, size)) {
                result.add(linkTo(methodOn(TagController.class).readAllTags(page + 1, size)).withRel("next"));
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
    public ResponseEntity<?> deleteTag(@Valid @RequestBody TagDTO dto) {
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

    private List<TagDTO> buildSelfLinks(List<TagDTO> tags) {
        return tags.stream()
                .map(t -> t
                        .add(linkTo(TagController.class).slash(t.getId()).withSelfRel()))
                .collect(Collectors.toList());
    }

    private boolean hasNext(int page, int size) {
        int lastPage = service.getLastPage(size);
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
