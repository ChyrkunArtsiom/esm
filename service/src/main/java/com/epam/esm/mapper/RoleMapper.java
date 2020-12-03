package com.epam.esm.mapper;

import com.epam.esm.dto.RoleDTO;
import com.epam.esm.entity.Role;

/**
 * Class that converts {@link Role} and {@link RoleDTO} objects to each other.
 */
public class RoleMapper {
    /**
     * Converts {@link RoleDTO} object to a {@link Role}.
     *
     * @param dto the {@link RoleDTO} object
     * @return the {@link Role} object
     */
    public static Role toEntity(RoleDTO dto) {
        Role entity = new Role();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        return entity;
    }

    /**
     * Converts {@link Role} object to a {@link RoleDTO}.
     *
     * @param entity the {@link Role} object
     * @return the {@link RoleDTO} object
     */
    public static RoleDTO toDto(Role entity) {
        return new RoleDTO(entity.getId(), entity.getName());
    }
}
