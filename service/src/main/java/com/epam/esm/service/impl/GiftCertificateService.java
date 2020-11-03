package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.InputSanitizer;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.validator.GiftCertificateDTOValidator;
import com.epam.esm.validator.SearchCriteriaValidator;
import com.epam.esm.validator.TagDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with {@link GiftCertificateDAO}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = {GiftCertificateDAO.class, TagService.class})
public class GiftCertificateService implements AbstractService<GiftCertificateDTO> {

    private GiftCertificateDAO dao;

    private TagService tagService;

    /**
     * Sets {@link GiftCertificateDAO} object.
     *
     * @param dao the {@link GiftCertificateDAO} object
     */
    @Autowired
    public void setDao(GiftCertificateDAO dao) {
        this.dao = dao;
    }

    /**
     * Sets {@link TagService} object.
     *
     * @param tagService the {@link TagService} object.
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDTO read(int id) {
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
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
        GiftCertificateDTOValidator.isValid(dto);
        dto.setDescription(InputSanitizer.sanitize(dto.getDescription()));
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        if (entity.getTags() != null) {
            for (String tag : entity.getTags()) {
                try {
                    tagService.read(tag);
                } catch (NoTagException ex) {
                    tagService.create(new TagDTO(0, tag));
                }
            }
        }
        entity = dao.create(entity);
        return GiftCertificateMapper.toDto(entity);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        if (dto.getName() != null) {
            GiftCertificateDTOValidator.isNameValid(dto.getName());
            try {
                GiftCertificate toUpdate = dao.read(dto.getName());
                GiftCertificate substitute = GiftCertificateMapper.toEntity(dto);
                if (dto.getPrice() != null) {
                    GiftCertificateDTOValidator.isPriceValid(dto.getPrice());
                    toUpdate.setPrice(substitute.getPrice());
                }
                if (dto.getDescription() != null) {
                    GiftCertificateDTOValidator.isDescriptionValid(dto.getDescription());
                    toUpdate.setDescription(substitute.getDescription());
                }
                if (dto.getDuration() != null) {
                    GiftCertificateDTOValidator.isDurationValid(dto.getDuration());
                    toUpdate.setDuration(substitute.getDuration());
                }
                if (dto.getTags() != null) {
                    for (String tag : dto.getTags()) {
                        TagDTOValidator.isValid(new TagDTO(0, tag));
                    }
                    toUpdate.setTags(substitute.getTags());
                    for (String tag : toUpdate.getTags()) {
                        try {
                            tagService.read(tag);
                        } catch (NoTagException ex) {
                            tagService.create(new TagDTO(0, tag));
                        }
                    }
                }
                dao.update(toUpdate);
                return null;
            } catch (NoCertificateException ex) {
                return create(dto);
            }
        } else {
            throw new CertificateNameIsNotPresentException("Certificate name is not presented", "");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> readByParams(SearchCriteria criteria) {
        List<GiftCertificateDTO> dtos = new ArrayList<>();
        SearchCriteriaValidator.isValid(criteria);
        List<GiftCertificate> certificates = dao.readByParams(criteria);
        for (GiftCertificate c : certificates) {
            dtos.add(GiftCertificateMapper.toDto(c));
        }
        return dtos;
    }
}
