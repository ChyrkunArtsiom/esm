package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
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

@RestController
@ComponentScan(basePackageClasses = {TagService.class, EsmExceptionHandler.class})
@RequestMapping("/tags")
@Validated
public class TagController {

    private static final String TAGS_PATH = "/tags/";

    private AbstractService<Tag, TagDTO> service;

    @Autowired
    public void setService(AbstractService<Tag, TagDTO> service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO dto,
                                            UriComponentsBuilder ucb) {

        TagDTO createdTag = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(TAGS_PATH).path(String.valueOf(createdTag.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(createdTag, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO readTag(@PathVariable @Positive @Digits(integer = 4, fraction = 0) int tagId) {
        return service.read(tagId);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteTag(@Valid @RequestBody TagDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> readAllTags() {
        return service.readAll();
    }
}
