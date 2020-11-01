package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.exception.DAOException;
import com.epam.esm.dao.util.SearchCriteria;

import java.util.List;

public interface AbstractService<T extends AbstractEntity, U extends AbstractDTO> {
    U create(U entity);
    U read(int id) throws DAOException;
    U update(U entity);
    boolean delete(U dto);
    List<U> readAll();
    List<U> readByParams(SearchCriteria criteria);
}
