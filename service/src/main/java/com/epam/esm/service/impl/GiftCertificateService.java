package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DAOException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.service.PostgresqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackageClasses = {GiftCertificateDAO.class, TagService.class})
public class GiftCertificateService implements PostgresqlService<GiftCertificate, GiftCertificateDTO> {

    private GiftCertificateDAO dao;

    private TagService tagService;

    @Autowired
    public void setDao(GiftCertificateDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public int create(GiftCertificate certificate) {
        return dao.create(certificate);
    }

    @Override
    public GiftCertificateDTO read(int id) throws DAOException {
        Optional<GiftCertificate> certificate = dao.read(id);
        //Exception or Optional.empty?
        return certificate.map(GiftCertificateMapper::toDto).orElse(null);
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate certificate) {
        return dao.update(certificate);
    }

    @Override
    public boolean delete(GiftCertificate certificate) {
        return dao.delete(certificate);
    }

    @Override
    public List<GiftCertificate> readAll() {
        return dao.readAll();
    }
}
