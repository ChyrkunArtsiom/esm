package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.PostgresqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackages = "com.epam.esm")
public class TagService implements PostgresqlService<Tag> {

    private TagDAO dao;

    @Autowired
    public void setDao(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean create(Tag tag) {
        return dao.create(tag);
    }

    @Override
    public Optional<Tag> read(int id) {
        return dao.read(id);
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
