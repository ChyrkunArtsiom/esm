package com.epam.esm.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDTOTest {

    @Test
    public void testEquals() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO user = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        assertEquals(user, user);
    }

    @Test
    public void testEqualsWrongObject() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO user = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        assertNotEquals(user, "String");
    }

    @Test
    public void testToString() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO tag = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        String expected = "User id: 1, username: tag, first name: Ivan, second name: Ivanov, " +
                "birthday: "+ LocalDate.now().toString() + ", role: {Role id: 1, name: ROLE_USER}";
        assertEquals(expected, tag.toString());
    }
}
