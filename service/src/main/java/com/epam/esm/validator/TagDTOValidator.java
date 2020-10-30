package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagNameIsNotValidException;

public class TagDTOValidator {
    public static boolean isValid(TagDTO dto) throws TagNameIsNotValidException{
        if (dto.getName().matches("^[a-zA-ZА-Яа-я]{3,45}$")) {
            return true;
        } else {
            throw new TagNameIsNotValidException(dto.getName());
        }
    }
}
