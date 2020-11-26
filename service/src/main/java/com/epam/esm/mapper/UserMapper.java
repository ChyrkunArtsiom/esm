package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;

/**
 * Class that converts {@link User} and {@link UserDTO} objects to each other.
 */
public class UserMapper {

    /**
     * Converts {@link UserDTO} object to a {@link User}.
     *
     * @param dto the {@link UserDTO} object
     * @return the {@link User} object
     */
    public static User toEntity(UserDTO dto) {
        User entity = new User();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    /**
     * Converts {@link User} object to a {@link UserDTO}.
     *
     * @param entity the {@link User} object
     * @return the {@link UserDTO} object
     */
    public static UserDTO toDto(User entity) {
        return new UserDTO(entity.getId(), entity.getName(), entity.getPassword());
    }
}
