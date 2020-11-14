package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TagDTOTest {

    @Test
    public void testEqualsSameTag() {
        Tag tag = new Tag("tag");
        assertEquals(tag, tag);
    }

    @Test
    public void testEqualsWrongObject() {
        Tag tag = new Tag("tag");
        assertNotEquals(tag, "String");
    }

    @Test
    public void testToString() {
        Tag tag = new Tag(1, "tag");
        String expected = "Tag id: 1, name: tag";
        assertEquals(expected, tag.toString());
    }
}