package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
import java.util.List;

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
     * @param ucb the {@link UriComponentsBuilder} which creates URI of created object
     * @return the {@link ResponseEntity} object with {@link TagDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO dto,
                                            UriComponentsBuilder ucb) {

        TagDTO createdTag = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(TAGS_PATH).path(String.valueOf(createdTag.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(createdTag, headers, HttpStatus.OK);
    }

    /**
     * Gets {@link TagDTO} object by id.
     *
     * @param tagId the {@link TagDTO} object id
     * @return the {@link TagDTO} object
     */
    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO readTag(@PathVariable @Positive @Digits(integer = 4, fraction = 0) int tagId) {
        return service.read(tagId);
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
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> readAllTags() {
        return service.readAll();
    }
}
