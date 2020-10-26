package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

public class TagMapper {

    public static Tag toEntity(TagDTO dto) {
        return new Tag(dto.getId(), dto.getName());
    }

    public static TagDTO toDto(Tag entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }
}
