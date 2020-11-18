package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private static UserDTO dto;
    private static User entity;

    @BeforeAll
    static void setUp() {
        entity = new User(1, "name", "password".toCharArray());
        dto = new UserDTO(1, "name", "password".toCharArray());
    }

    @Test
    public void testToEntity() {
        User converted = UserMapper.toEntity(dto);
        assertEquals(entity, converted);
    }

    @Test
    public void testToDto() {
        UserDTO converted = UserMapper.toDto(entity);
        assertEquals(dto, converted);
    }
}
