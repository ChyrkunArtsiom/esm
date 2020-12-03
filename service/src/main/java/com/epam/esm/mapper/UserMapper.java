package com.epam.esm.mapper;

import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.time.LocalDate;

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
        if (dto.getPassword() != null) {
            entity.setPassword(dto.getPassword().toCharArray());
        }
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        if (dto.getBirthday() != null) {
            LocalDate date = LocalDate.parse(dto.getBirthday());
            entity.setBirthday(date);
        }
        if (dto.getRole() != null) {
            Role role = RoleMapper.toEntity(dto.getRole());
            entity.setRole(role);
        }
        return entity;
    }

    /**
     * Converts {@link User} object to a {@link UserDTO}.
     *
     * @param entity the {@link User} object
     * @return the {@link UserDTO} object
     */
    public static UserDTO toDto(User entity) {
        RoleDTO roleDTO = RoleMapper.toDto(entity.getRole());
        return new UserDTO(entity.getId(), entity.getName(),
                String.valueOf(entity.getPassword()), entity.getFirstName(),
                entity.getSecondName(), entity.getBirthday().toString(), roleDTO);
    }

    public static UserViewDTO toUserViewDTO(User entity) {
        return new UserViewDTO(entity.getId(), entity.getName(),
                entity.getFirstName(),
                entity.getSecondName(), entity.getBirthday().toString());
    }
}
