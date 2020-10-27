package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.PostgresqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackageClasses = TagDAO.class)
public class TagService implements PostgresqlService<Tag, TagDTO> {

    private TagDAO dao;

    @Autowired
    public void setDao(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    public int create(Tag tag) {
        return dao.create(tag);
    }

    @Override
    public TagDTO read(int id) throws DAOException {
        Optional<Tag> optionalTag = dao.read(id);
        //Exception or Optional.empty?
        return optionalTag.map(TagMapper::toDto).orElse(null);
    }

    @Override
    public Optional<Tag> update(Tag tag) {
        return dao.update(tag);
    }

    @Override
    public boolean delete(Tag tag) {
        return dao.delete(tag);
    }

    @Override
    public List<Tag> readAll() {
        return dao.readAll();
    }
}
