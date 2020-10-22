package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface PostgresqlDAO<T extends Entity> {
    boolean create(T entity);
    Optional<T> read(int id);
    Optional<T> update(T entity);
    boolean delete(T entity);
    List<T> readAll();

}
