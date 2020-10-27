package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackageClasses = GiftCertificateService.class)
@RequestMapping("/certificates")
public class GiftCertificateController {

    private GiftCertificateService service;

    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{certificateId}", method = RequestMethod.GET, produces = "application/json")
    public GiftCertificateDTO readCertificate(@PathVariable int certificateId) {
        return service.read(certificateId);
    }

}
