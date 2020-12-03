package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.service.impl.TagService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Class controller for interacting with {@link TagDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {TagService.class})
@RequestMapping("/tags")
@Validated
public class TagController extends AbstractController<TagService, TagLinkBuilder, TagDTO> {

    @Override
    @Autowired
    public void setService(TagService service) {
        super.setService(service);
    }

    @Override
    @Autowired
    public void setLinkBuilder(TagLinkBuilder linkBuilder) {
        super.setLinkBuilder(linkBuilder);
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
     * Gets all {@link TagDTO} objects.
     *
     * @return the list of {@link TagDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAllTags(
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        return readPaginatedForController(page, size);
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
}
