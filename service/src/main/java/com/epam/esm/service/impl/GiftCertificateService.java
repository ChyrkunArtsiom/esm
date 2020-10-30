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
        GiftCertificate certificate = dao.read(id);
        return GiftCertificateMapper.toDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity = dao.update(entity);
        return GiftCertificateMapper.toDto(entity);
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
