package com.epam.esm.service;

import com.epam.esm.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface PostgresqlService<T extends Entity> {
    boolean create(T entity);
    Optional<T> read(int id);
    Optional<T> update(T entity);
    boolean delete(T entity);
    List<T> readAll();
}
