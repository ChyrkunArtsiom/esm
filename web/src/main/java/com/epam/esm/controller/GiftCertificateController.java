package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackageClasses = {GiftCertificateService.class, EsmExceptionHandler.class})
@RequestMapping("/certificates")
public class GiftCertificateController {

    private AbstractService<GiftCertificate, GiftCertificateDTO> service;

    @Autowired
    public void setService(AbstractService<GiftCertificate, GiftCertificateDTO> service) {
        this.service = service;
    }

    @RequestMapping(value = "/{certificateId}", method = RequestMethod.GET, produces = "application/json")
    public GiftCertificateDTO readCertificate(@PathVariable int certificateId) {
        return service.read(certificateId);
    }

}
