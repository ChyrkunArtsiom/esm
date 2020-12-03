package com.epam.esm.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationUserTest {

    private static AuthenticationUser authenticationUser;
    private static UserDTO user;
    private static RoleDTO role;

    @BeforeAll
    static void setUp() {
        role = new RoleDTO(1, "ROLE_USER");
        user = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        authenticationUser = new AuthenticationUser(user);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, authenticationUser.getUser());
    }

    @Test
    public void testGetAuthorities() {
        assertEquals(Arrays.asList(role), authenticationUser.getAuthorities());
    }
}
