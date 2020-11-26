package com.epam.esm.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TagDTOTest {

    @Test
    public void testEqualsSameTag() {
        TagDTO tag = new TagDTO(1, "tag");
        assertEquals(tag, tag);
    }

    @Test
    public void testEqualsWrongObject() {
        TagDTO tag = new TagDTO(1, "tag");
        assertNotEquals(tag, "String");
    }

    @Test
    public void testToString() {
        TagDTO tag = new TagDTO(1, "tag");
        String expected = "Tag id: 1, name: tag";
        assertEquals(expected, tag.toString());
    }
}