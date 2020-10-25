package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

public class TagMapper implements ItemMapper<TagDTO, Tag> {
    @Override
    public Tag toEntity(TagDTO dto) {
        return new Tag(dto.getId(), dto.getName());
    }

    @Override
    public TagDTO toDto(Tag entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }
}
