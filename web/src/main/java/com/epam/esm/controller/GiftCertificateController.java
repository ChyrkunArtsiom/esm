package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.dto.validationmarkers.PutValidation;
import com.epam.esm.service.impl.GiftCertificateService;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.util.linkbuilders.GiftCertificateLinkBuilder;
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
 * Class controller for interacting with {@link GiftCertificateDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {GiftCertificateService.class})
@RequestMapping("/certificates")
@Validated
public class GiftCertificateController {

    private GiftCertificateService service;

    private GiftCertificateLinkBuilder linkBuilder;

    /**
     * Sets {@link GiftCertificateService} object.
     *
     * @param service the {@link GiftCertificateService} object
     */
    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @Autowired
    public void setLinkBuilder(GiftCertificateLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    /**
     * Creates {@link GiftCertificateDTO} object. Returns location and status.
     *
     * @param dto the {@link GiftCertificateDTO} object.
     * @return the {@link ResponseEntity} object with {@link GiftCertificateDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<GiftCertificateDTO> createCertificate(@Validated(value = PostValidation.class) @RequestBody GiftCertificateDTO dto) {
        GiftCertificateDTO createdCertificate = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(GiftCertificateController.class).slash(createdCertificate.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        linkBuilder.buildLink(createdCertificate);
        return new ResponseEntity<>(createdCertificate, headers, HttpStatus.OK);
    }

    /**
     * Gets {@link GiftCertificateDTO} object by id.
     *
     * @param certificateId the {@link GiftCertificateDTO} object id
     * @return the {@link GiftCertificateDTO} object
     */
    @RequestMapping(value = "/{certificateId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<GiftCertificateDTO> readCertificate(
            @PathVariable @Positive @Digits(integer = 10, fraction = 0) int certificateId) {
        GiftCertificateDTO certificate = service.read(certificateId);
        linkBuilder.buildLink(certificate);
        return certificate;
    }

    /**
     * Gets {@link GiftCertificateDTO} objects by parameters.
     *
     * @param searchCriteria the Search parameters
     * @param page           the page
     * @param size           the size
     * @return the list of {@link GiftCertificateDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readCertificatesByParams(
            SearchCriteria searchCriteria,
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<GiftCertificateDTO> certificates;
        CollectionModel result;
        certificates = service.readByParams(searchCriteria, page, size);
        linkBuilder.buildLinks(certificates);
        result = CollectionModel.of(certificates);
        if (Stream.of(page, size).noneMatch(Objects::isNull)) {
            int lastPage = service.getLastPage(searchCriteria, size);
            if (hasPrevious(page)) {
                linkBuilder.buildPreviousPageLink(result, searchCriteria, page, size);
            }
            if (hasNext(page, lastPage)) {
                linkBuilder.buildNextPageLink(result, searchCriteria, page, size);
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
    public ResponseEntity<?> updateCertificate(
            @RequestBody @Validated(value = PutValidation.class) GiftCertificateDTO dto) {
        GiftCertificateDTO created = service.update(dto);
        if (created != null) {
            HttpHeaders headers = new HttpHeaders();
            Link selfLink = linkTo(GiftCertificateController.class).slash(created.getId()).withSelfRel();
            headers.setLocation(selfLink.toUri());
            linkBuilder.buildLink(created);
            return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Deletes {@link GiftCertificateDTO} object.
     *
     * @param dto the {@link GiftCertificateDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteCertificate(
            @RequestBody @Validated (value = DeleteValidation.class) GiftCertificateDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes {@link GiftCertificateDTO} object.
     *
     * @param tagId the id of {@link GiftCertificateDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(value = "/{tagId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificateByUrlId(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int tagId) {
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
