package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.validator.TagDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link Tag}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = TagDAO.class)
public class TagService implements AbstractService<TagDTO> {

    private TagDAO dao;

    /**
     * Sets {@link TagDAO} object.
     *
     * @param dao the {@link TagDAO} object
     */
    @Autowired
    public void setDao(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO dto) {
        TagDTOValidator.isValid(dto);
        Tag entity = TagMapper.toEntity(dto);
        entity = dao.create(entity);
        return TagMapper.toDto(entity);
    }

    @Override
    public TagDTO read(int id) {
        Tag tag = dao.read(id);
        return TagMapper.toDto(tag);
    }

    /**
     * Gets {@link TagDTO} object by the name.
     *
     * @param name the name string
     * @return the {@link TagDTO} object
     */
    public TagDTO read(String name) {
        Tag tag = dao.read(name);
        return TagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> readAll() {
        List<TagDTO> dtos;
        List<Tag> entities = dao.readAll();
        dtos = entities.stream().map(TagMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    public boolean delete(TagDTO dto) {
        Tag entity = TagMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    public List<TagDTO> readByParams(SearchCriteria criteria) {
        throw new UnsupportedOperationException("Tag is not supported by search method.");
    }
}
