package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.GiftCertificateService;
import com.epam.esm.util.SearchCriteria;
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
 * Class controller for interacting with {@link GiftCertificateDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {GiftCertificateService.class, EsmExceptionHandler.class})
@RequestMapping("/certificates")
@Validated
public class GiftCertificateController {

    private AbstractService<GiftCertificateDTO> service;

    private static final String CERTIFICATES_PATH = "/certificates/";

    /**
     * Sets {@link AbstractService} object.
     *
     * @param service the {@link AbstractService} object
     */
    @Autowired
    public void setService(AbstractService<GiftCertificateDTO> service) {
        this.service = service;
    }

    /**
     * Creates {@link GiftCertificateDTO} object. Returns location and status.
     *
     * @param dto the {@link GiftCertificateDTO} object.
     * @param ucb the {@link UriComponentsBuilder} which creates URI of created object
     * @return the {@link ResponseEntity} object with {@link GiftCertificateDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<GiftCertificateDTO> createCertificate(@Valid @RequestBody GiftCertificateDTO dto,
                                                                UriComponentsBuilder ucb) {
        GiftCertificateDTO createdCertificate = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(CERTIFICATES_PATH).path(String.valueOf(createdCertificate.getId())).build().toUri();
        headers.setLocation(locationUri);
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
    public GiftCertificateDTO readCertificate(
            @PathVariable @Positive @Digits(integer = 4, fraction = 0) int certificateId) {
        return service.read(certificateId);
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
    public List<GiftCertificateDTO> readCertificatesByParams(
            @RequestParam(value = "tag", defaultValue = "") String tagName,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "sort", defaultValue = "name_asc") String sort) {

        return service.readByParams(new SearchCriteria(tagName, name, description, sort));
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

}
