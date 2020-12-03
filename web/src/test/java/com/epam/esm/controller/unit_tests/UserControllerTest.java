package com.epam.esm.controller.unit_tests;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.exception.NoUserException;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.util.jwt.JwtUtilImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    private final static String USERS_PATH = "/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private JwtUtilImpl jwtUtil;

    private static AuthenticationUser user;
    private static String jwt;

    @BeforeAll
    public static void setUp() {
        RoleDTO role = new RoleDTO(1, "ROLE_ADMIN");
        UserDTO userDTO = new UserDTO(1, "admin", "password", "Artsiom", "Chyrkun", "1994-06-18", role);
        user = new AuthenticationUser(userDTO);
        jwt = "Bearer test";
    }

    @BeforeEach
    public void setUpAuthorizationMock() {
        Mockito.when(jwtUtil.getUsernameFromToken(Mockito.anyString())).thenReturn(user.getUsername());
        Mockito.when(service.loadUserByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(jwtUtil.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);
    }

    @Test
    public void testGetUser() throws Exception {
        UserViewDTO userDTO = new UserViewDTO(1, "tag",
                "Ivan", "Ivanov", LocalDate.now().toString());
        Mockito.when(service.read(userDTO.getId())).thenReturn(userDTO);

        mockMvc.perform(get(USERS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMissingUser() throws Exception {
        String id = "1";
        NoUserException ex = new NoUserException(id);
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("userDoesntExist"), id);

        mockMvc.perform(get(USERS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserViewDTO> dtos = new ArrayList<>(
                Arrays.asList(
                        new UserViewDTO(1, "name1",
                                "Ivan", "Ivanov", LocalDate.now().toString()),
                        new UserViewDTO(2, "name2",
                                "Petr", "Petrov", LocalDate.now().toString())));
        Mockito.when(service.readAll()).thenReturn(dtos);


        mockMvc.perform(get(USERS_PATH).header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.users.[0].name").value("name1"))
                .andExpect(jsonPath("$._embedded.users.[1].name").value("name2"))
                .andExpect(status().isOk());
    }
}
