package com.epam.esm.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RoleDTOTest {

    @Test
    public void testEquals() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        assertEquals(role, role);
    }

    @Test
    public void testEqualsWrongObject() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        RoleDTO admin = new RoleDTO(1, "ROLE_ADMIN");
        assertNotEquals(role, admin);
    }

    @Test
    public void testToString() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        String expected = "Role id: 1, name: ROLE_USER";
        assertEquals(expected, role.toString());
    }
}
