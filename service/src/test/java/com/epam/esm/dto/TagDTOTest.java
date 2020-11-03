package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOTest {

    @Test
    void testEqualsSameTag() {
        Tag tag = new Tag(1, "tag");
        assertEquals(tag, tag);
    }

    @Test
    void testEqualsWrongObject() {
        Tag tag = new Tag(1, "tag");
        assertNotEquals(tag, "String");
    }

    @Test
    void testToString() {
        Tag tag = new Tag(1, "tag");
        String expected = "Tag id: 1, name: tag";
        assertEquals(expected, tag.toString());
    }
}