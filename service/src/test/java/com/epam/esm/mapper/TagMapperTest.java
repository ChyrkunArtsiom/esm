package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {

    private static TagDTO dto;
    private static Tag entity;
    private static TagMapper mapper = new TagMapper();

    @BeforeAll
    static void setUp() {
        entity = new Tag(1, "Tag name");
        dto = new TagDTO(1, "Tag name");
    }

    @Test
    void testToEntity() {
        Tag converted = mapper.toEntity(dto);
        assertEquals(entity, converted);
    }

    @Test
    void testToDto() {
        TagDTO converted = mapper.toDto(entity);
        assertEquals(dto, converted);
    }
}