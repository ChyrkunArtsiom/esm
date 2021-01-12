package com.epam.esm.util.jwt;

import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = JwtUtilImpl.class)
class JwtUtilImplTest {

    private static String token;

    @Autowired
    private JwtUtilImpl jwtGenerator;

    private static AuthenticationUser authenticationUser;

    @BeforeAll
    static void setUp() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO user = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        authenticationUser = new AuthenticationUser(user);
    }

    @BeforeEach
    void generateToken() {
        token = jwtGenerator.generateToken(authenticationUser);
    }

    @Test
    void testGetUsernameFromToken() {
        assertEquals(authenticationUser.getUsername(), jwtGenerator.getUsernameFromToken(token));
    }

    @Test
    void testGetUserIdFromToken() {
        assertEquals(authenticationUser.getId(), jwtGenerator.getUserIdFromToken(token));
    }

    @Test
    void testIsTokenExpired() {
        assertFalse(jwtGenerator.isTokenExpired(token));
    }

    @Test
    void testValidateToken() {
        assertTrue(jwtGenerator.validateToken(token, authenticationUser));
    }

}