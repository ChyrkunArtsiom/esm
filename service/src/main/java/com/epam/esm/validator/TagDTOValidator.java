package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagNameIsNotValidException;

/**
 * Class validator for {@link TagDTO} objects.
 */
public class TagDTOValidator {
    /**
     * Validates {@link TagDTO} object.
     *
     * @param dto the {@link TagDTO} object to validate
     * @throws TagNameIsNotValidException if {@link TagDTO} object is not valid
     */
    public static void isValid(TagDTO dto) {
        if (!dto.getName().matches("^[a-zA-ZА-Яа-я]{3,45}$")) {
            throw new TagNameIsNotValidException(dto.getName());
        }
    }
}
