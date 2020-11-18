package com.epam.esm.dto;

import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDTOTest {

    @Test
    public void testEqualsSameTag() {
        UserDTO user = new UserDTO(1, "tag", "password".toCharArray());
        assertEquals(user, user);
    }

    @Test
    public void testEqualsWrongObject() {
        UserDTO user = new UserDTO(1, "user", "password".toCharArray());
        assertNotEquals(user, "String");
    }

    @Test
    public void testToString() {
        UserDTO tag = new UserDTO(1, "tag", "password".toCharArray());
        String expected = "User id: 1, name: tag";
        assertEquals(expected, tag.toString());
    }
}
