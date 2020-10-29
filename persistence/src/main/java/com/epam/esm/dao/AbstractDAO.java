package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractDAO<T extends AbstractEntity> {
    T create(T entity);
    Optional<T> read(int id);
    Optional<T> update(T entity);
    boolean delete(T entity);
    List<T> readAll();
}
