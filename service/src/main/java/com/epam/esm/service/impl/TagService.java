package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        //Here will be validation
        Tag entity = TagMapper.toEntity(dto);
        entity = dao.create(entity);
        return TagMapper.toDto(entity);
    }

    @Override
    public TagDTO read(int id) throws DAOException {
        Optional<Tag> optionalTag = dao.read(id);
        //Exception is thrown for empty optional, so what to return
        return optionalTag.map(TagMapper::toDto).orElse(null);
    }

    public TagDTO read(String name) throws DAOException {
        Optional<Tag> optionalTag = dao.read(name);
        //Exception is thrown for empty optional, so what to return
        return optionalTag.map(TagMapper::toDto).orElse(null);
    }

    @Override
    public Optional<TagDTO> update(TagDTO dto) {
        try {
            read(dto.getName());
            //If DAOException is thrown it means that tag doesn't exist and has to be created. Otherwise unsupported.
        } catch (DAOException ex) {
            return Optional.of(create(dto));
        }
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    public boolean delete(TagDTO dto) {
        Tag entity = TagMapper.toEntity(dto);
        return dao.delete(entity);
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
}
