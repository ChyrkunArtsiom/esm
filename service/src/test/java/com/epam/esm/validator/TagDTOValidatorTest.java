package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TagDTOValidatorTest {

    @Test
    void isValid() {
        TagDTO dto = new TagDTO(1, "name");
        assertTrue(TagDTOValidator.isValid(dto));
    }
}