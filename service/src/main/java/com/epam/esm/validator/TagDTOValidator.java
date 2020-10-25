package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;

public class TagDTOValidator {
    public static boolean isValid(TagDTO dto) {
        return dto.getName().matches("^[a-zA-ZА-Яа-я]{1,45}$");
    }
}
