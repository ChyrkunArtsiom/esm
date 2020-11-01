package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.GiftCertificateService;
import com.epam.esm.dao.util.SearchCriteria;
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
@ComponentScan(basePackageClasses = {GiftCertificateService.class, EsmExceptionHandler.class})
@RequestMapping("/certificates")
@Validated
public class GiftCertificateController {

    private AbstractService<GiftCertificate, GiftCertificateDTO> service;

    private static final String CERTIFICATES_PATH = "/certificates/";

    @Autowired
    public void setService(AbstractService<GiftCertificate, GiftCertificateDTO> service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<GiftCertificateDTO> createCertificate(@Valid @RequestBody GiftCertificateDTO dto,
                                                                UriComponentsBuilder ucb) {
        GiftCertificateDTO createdCertificate = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(CERTIFICATES_PATH).path(String.valueOf(createdCertificate.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(createdCertificate, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{certificateId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO readCertificate(
            @PathVariable @Positive @Digits(integer = 4, fraction = 0) int certificateId) {
        return service.read(certificateId);
    }

/*    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDTO> readAllCertificates() {
        return service.readAll();
    }*/

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteCertificate(@RequestBody GiftCertificateDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDTO> readCertificatesByParams(
            @RequestParam(value = "tag", defaultValue = "") String tagName,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "sort", defaultValue = "name_asc") String sort) {
        return service.readByParams(new SearchCriteria(tagName, name, description, sort));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<?> updateCertificate(@RequestBody GiftCertificateDTO dto) {
        if (service.update(dto) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
