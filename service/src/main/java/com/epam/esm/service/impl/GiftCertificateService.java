package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DAOException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackageClasses = {GiftCertificateDAO.class, TagService.class})
public class GiftCertificateService implements AbstractService<GiftCertificate, GiftCertificateDTO> {

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
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity = dao.create(entity);
        return GiftCertificateMapper.toDto(entity);
    }

    @Override
    public GiftCertificateDTO read(int id) throws DAOException {
        Optional<GiftCertificate> certificate = dao.read(id);
        //Exception or Optional.empty?
        return certificate.map(GiftCertificateMapper::toDto).orElse(null);
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDTO> update(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        Optional<GiftCertificate> optionalGiftCertificate = dao.update(entity);
        if (optionalGiftCertificate.isPresent()) {
            entity = optionalGiftCertificate.get();
            return Optional.of(GiftCertificateMapper.toDto(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    public List<GiftCertificateDTO> readAll() {
        List<GiftCertificateDTO> dtos = new ArrayList<>();
        List<GiftCertificate> certificates = dao.readAll();

        for (GiftCertificate c : certificates) {
            dtos.add(GiftCertificateMapper.toDto(c));
        }
        return dtos;
    }
}
