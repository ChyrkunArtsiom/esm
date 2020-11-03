package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagNameIsNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOValidatorTest {

    @Test
    void isValid() {
        TagDTO dto = new TagDTO(1, "name");
        assertTrue(TagDTOValidator.isValid(dto));
    }

    @Test
    void testInvalidName() {
        TagDTO dto = new TagDTO(1, "tt");
        TagNameIsNotValidException thrown = assertThrows(TagNameIsNotValidException.class,
                () -> TagDTOValidator.isValid(dto));
        assertEquals(dto.getName(), thrown.getValue());
    }
}