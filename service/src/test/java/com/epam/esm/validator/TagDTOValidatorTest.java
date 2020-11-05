package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagNameIsNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOValidatorTest {

    @Test
    public void isValid() {
        TagDTO dto = new TagDTO(1, "name");
        assertDoesNotThrow(() -> TagDTOValidator.isValid(dto));
    }

    @Test
    public void testInvalidName() {
        TagDTO dto = new TagDTO(1, "tt");
        TagNameIsNotValidException thrown = assertThrows(TagNameIsNotValidException.class,
                () -> TagDTOValidator.isValid(dto));
        assertEquals(dto.getName(), thrown.getValue());
    }
}