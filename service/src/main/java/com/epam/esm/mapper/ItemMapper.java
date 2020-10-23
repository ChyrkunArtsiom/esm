package com.epam.esm.mapper;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.entity.AbstractEntity;

public interface ItemMapper<T extends AbstractDTO, U extends AbstractEntity> {
    U toEntity(T dto);
    T toDto(U entity);
}
