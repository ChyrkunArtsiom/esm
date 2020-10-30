package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.List;

public interface AbstractDAO<T extends AbstractEntity> {
    T create(T entity);
    T read(int id);
    T update(T entity);
    boolean delete(T entity);
    List<T> readAll();
}
