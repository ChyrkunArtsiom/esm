package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.dao.util.SearchCriteria;
import com.epam.esm.validator.TagDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackageClasses = TagDAO.class)
public class TagService implements AbstractService<Tag, TagDTO> {

    private TagDAO dao;

    @Autowired
    public void setDao(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    public TagDTO create(TagDTO dto) {
        if (TagDTOValidator.isValid(dto)) {
            Tag entity = TagMapper.toEntity(dto);
            entity = dao.create(entity);
            return TagMapper.toDto(entity);
        }
        return null;
    }

    @Override
    public TagDTO read(int id) throws DAOException {
        Tag tag = dao.read(id);
        return TagMapper.toDto(tag);
    }

    public TagDTO read(String name) throws DAOException {
        Tag tag = dao.read(name);
        return TagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> readAll() {
        List<TagDTO> dtos = new ArrayList<>();
        List<Tag> tags = dao.readAll();

        for (Tag t : tags) {
            dtos.add(TagMapper.toDto(t));
        }
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
