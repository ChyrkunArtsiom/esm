package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {

    private static TagDTO dto;
    private static Tag entity;

    @BeforeAll
    static void setUp() {
        entity = new Tag(1, "Tag name");
        dto = new TagDTO(1, "Tag name");
    }

    @Test
    public void testToEntity() {
        Tag converted = TagMapper.toEntity(dto);
        assertEquals(entity, converted);
    }

    @Test
    public void testToDto() {
        TagDTO converted = TagMapper.toDto(entity);
        assertEquals(dto, converted);
    }
}