package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

/**
 * Class that converts {@link Tag} and {@link TagDTO} objects to each other.
 */
public class TagMapper {

    /**
     * Converts {@link TagDTO} object to a {@link Tag}.
     *
     * @param dto the {@link TagDTO} object
     * @return the {@link Tag} object
     */
    public static Tag toEntity(TagDTO dto) {
        Tag entity = new Tag();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        return entity;
    }

    /**
     * Converts {@link Tag} object to a {@link TagDTO}.
     *
     * @param entity the {@link Tag} object
     * @return the {@link TagDTO} object
     */
    public static TagDTO toDto(Tag entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }
}
