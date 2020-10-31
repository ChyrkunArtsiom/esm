package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.CertificateNameIsNotPresentException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.validator.GiftCertificateDTOValidator;
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
    @Transactional(readOnly = true)
    public GiftCertificateDTO read(int id) throws DAOException {
        GiftCertificate certificate = dao.read(id);
        return GiftCertificateMapper.toDto(certificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> readAll() {
        List<GiftCertificateDTO> dtos = new ArrayList<>();
        List<GiftCertificate> certificates = dao.readAll();

        for (GiftCertificate c : certificates) {
            dtos.add(GiftCertificateMapper.toDto(c));
        }
        return dtos;
    }

    @Override
    public boolean delete(GiftCertificateDTO dto) {
        if (dto.getName() == null) {
            throw new CertificateNameIsNotPresentException("Certificate name is not presented", "");
        }
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    @Transactional(noRollbackFor = NoTagException.class)
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
        if (GiftCertificateDTOValidator.isValid(dto)) {
            GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
            for (String tag : entity.getTags()) {
                try {
                    tagService.read(tag);
                } catch (NoTagException ex) {
                    tagService.create(new TagDTO(0, tag));
                }
            }
            entity = dao.create(entity);
            return GiftCertificateMapper.toDto(entity);
        }
        return null;
    }






    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity = dao.update(entity);
        return GiftCertificateMapper.toDto(entity);
    }
}
