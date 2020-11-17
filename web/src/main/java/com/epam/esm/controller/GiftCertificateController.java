package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.GetParamIsNotPresent;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.GiftCertificateService;
import com.epam.esm.util.SearchCriteria;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class controller for interacting with {@link GiftCertificateDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {GiftCertificateService.class, EsmExceptionHandler.class})
@RequestMapping("/certificates")
@Validated
public class GiftCertificateController {

    private GiftCertificateService service;

    /**
     * Sets {@link AbstractService} object.
     *
     * @param service the {@link AbstractService} object
     */
    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Creates {@link GiftCertificateDTO} object. Returns location and status.
     *
     * @param dto the {@link GiftCertificateDTO} object.
     * @return the {@link ResponseEntity} object with {@link GiftCertificateDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<GiftCertificateDTO> createCertificate(@Valid @RequestBody GiftCertificateDTO dto) {
        GiftCertificateDTO createdCertificate = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(TagController.class).slash(createdCertificate.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        createdCertificate.add(selfLink);
        return new ResponseEntity<>(createdCertificate, headers, HttpStatus.OK);
    }

    /**
     * Gets {@link GiftCertificateDTO} object by id.
     *
     * @param certificateId the {@link GiftCertificateDTO} object id
     * @return the {@link GiftCertificateDTO} object
     */
    @RequestMapping(value = "/{certificateId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<GiftCertificateDTO> readCertificate(
            @PathVariable @Positive @Digits(integer = 4, fraction = 0) int certificateId) {
        GiftCertificateDTO certificate = service.read(certificateId);
        Link selfLink = linkTo(GiftCertificateController.class).slash(certificate.getId()).withSelfRel();
        certificate.add(selfLink);
        buildTagsSelfLink(certificate);
        return certificate;
    }

    /**
     * Deletes {@link GiftCertificateDTO} object.
     *
     * @param dto the {@link GiftCertificateDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteCertificate(@RequestBody GiftCertificateDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets {@link GiftCertificateDTO} objects by parameters.
     *
     * @param tagName     the tag name
     * @param name        the name of certificate
     * @param description the description
     * @param sort        the sort parameter
     * @return the list of {@link GiftCertificateDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readCertificatesByParams(
            @RequestParam(value = "tag", required = false, defaultValue = "") String tagName,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "sort", required = false, defaultValue = "name_asc") String sort,
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<GiftCertificateDTO> certificates;
        CollectionModel result;
        SearchCriteria searchCriteria = new SearchCriteria(tagName, name, description, sort);
        if (Stream.of(page, size).allMatch(Objects::isNull)) {
            certificates = service.readWithParams(new SearchCriteria(tagName, name, description, sort), null, null);
            certificates = buildSelfLinks(certificates);
            result = CollectionModel.of(certificates);
        } else if (Stream.of(page, size).anyMatch(Objects::isNull)) {
            throw new GetParamIsNotPresent();
        } else {
            int lastPage = service.getLastPage(searchCriteria, size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            certificates = service.readWithParams(searchCriteria, page, size);
            certificates = buildSelfLinks(certificates);
            result = CollectionModel.of(certificates);
            if (hasPrevious(page)) {
                result.add(linkTo(methodOn(GiftCertificateController.class)
                        .readCertificatesByParams(tagName, name, description, sort, page - 1, size))
                        .withRel("prev"));
            }
            if (hasNext(page, lastPage)) {
                result.add(linkTo(methodOn(GiftCertificateController.class)
                        .readCertificatesByParams(tagName, name, description, sort, page + 1, size))
                        .withRel("next"));
            }
        }

        return result;
    }

    /**
     * Updates {@link GiftCertificateDTO} object.
     *
     * @param dto the {@link GiftCertificateDTO} object to update.
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<?> updateCertificate(@RequestBody GiftCertificateDTO dto) {
        if (service.update(dto) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private List<GiftCertificateDTO> buildSelfLinks(List<GiftCertificateDTO> certificates) {
        return certificates.stream()
                .peek(this::buildTagsSelfLink)
                .collect(Collectors.toList());
    }

    private void buildTagsSelfLink(GiftCertificateDTO certificate) {
        if (certificate.getTags() != null) {
            Set<TagDTO> tags = certificate.getTags().stream().map(t ->
                    t.add(linkTo(TagController.class).slash(t.getId()).withSelfRel()))
                    .collect(Collectors.toSet());
            certificate.setTags(tags);
        }
    }

    private boolean hasNext(int page, int lastPage) {
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }

}
