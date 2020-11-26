package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
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
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link GiftCertificateDAO}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = {GiftCertificateDAO.class, TagService.class})
@Validated
public class GiftCertificateService implements AbstractService<GiftCertificateDTO> {

    private GiftCertificateDAO dao;

    private TagDAO tagDAO;

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
     * Sets {@link TagDAO} object.
     *
     * @param tagDAO the {@link TagDAO} object
     */
    @Autowired
    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
		GiftCertificateDTOValidator.isValid(dto);
        dto.setDescription(InputSanitizer.sanitize(dto.getDescription()));
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        if (entity.getTags() != null) {
            entity.setTags(checkTags(entity));
        }
        entity = dao.create(entity);
        return GiftCertificateMapper.toDto(entity);
    }

    @Override
    public GiftCertificateDTO read(int id) {
        GiftCertificate certificate = dao.read(id);
        return GiftCertificateMapper.toDto(certificate);
    }

    @Override
    public List<GiftCertificateDTO> readAll() {
        List<GiftCertificateDTO> dtos;
        List<GiftCertificate> certificates = dao.readAll();
        dtos = certificates.stream().map(GiftCertificateMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    /**
     * Gets the list of {@link GiftCertificateDTO} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param criteria the {@link SearchCriteria} object
     * @return the list of {@link GiftCertificateDTO} objects
     */
    public List<GiftCertificateDTO> readWithParams(SearchCriteria criteria, Integer page, Integer size) {
        List<GiftCertificateDTO> dtos;
        SearchCriteriaValidator.isValid(criteria);
        List<GiftCertificate> certificates = dao.readByParams(criteria, page, size);
        dtos = certificates.stream().map(GiftCertificateMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        try {
            GiftCertificate toUpdate = dao.read(dto.getName());
            GiftCertificate substitute = GiftCertificateMapper.toEntity(dto);
            if (dto.getPrice() != null) {
                toUpdate.setPrice(substitute.getPrice());
            }
            if (dto.getDescription() != null) {
                toUpdate.setDescription(substitute.getDescription());
            }
            if (dto.getDuration() != null) {
                toUpdate.setDuration(substitute.getDuration());
            }
            if (dto.getTags() != null) {
                toUpdate.setTags(checkTags(substitute));
            }
            dao.update(toUpdate);
            return null;
        } catch (NoCertificateException ex) {
            return create(dto);
        }
    }

    @Override
    @Transactional
    public boolean delete(GiftCertificateDTO dto) {
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return dao.delete(id);
    }

    private Set<Tag> checkTags(GiftCertificate certificate) {
        Set<Tag> newSetOfTags = new HashSet<>();
        for (Tag tag : certificate.getTags()) {
            TagDTOValidator.isValid(TagMapper.toDto(tag));
        }
        for (Tag tag : certificate.getTags()) {
            try {
                Tag oldTag = tagDAO.read(tag.getName());
                newSetOfTags.add(oldTag);
            } catch (NoTagException ex) {
                newSetOfTags.add(tag);
            }
        }
        return newSetOfTags;
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(SearchCriteria searchCriteria, Integer size) {
        return dao.getLastPage(searchCriteria, size);
    }
}
