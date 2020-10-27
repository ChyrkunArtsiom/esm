package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface PostgresqlService<T extends AbstractEntity, U extends AbstractDTO> {
    int create(T entity);
    U read(int id) throws DAOException;
    Optional<T> update(T entity);
    boolean delete(T entity);
    List<T> readAll();
}
