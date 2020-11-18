package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NoUserException;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.ErrorMessageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    private final static String USERS_PATH = "/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void testReadTag() throws Exception {
        UserDTO userDTO = new UserDTO(1, "name", "password".toCharArray());
        Mockito.when(service.read(userDTO.getId())).thenReturn(userDTO);

        mockMvc.perform(get(USERS_PATH + "/1"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadMissingTag() throws Exception {
        String id = "1";
        NoUserException ex = new NoUserException(id, 40403);
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("userDoesntExist"), id);

        mockMvc.perform(get(USERS_PATH + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testReadAllTags() throws Exception {
        List<UserDTO> dtos = new ArrayList<>(
                Arrays.asList(
                        new UserDTO(1, "name1", "password1".toCharArray()),
                        new UserDTO(2, "name2", "password2".toCharArray())));
        Mockito.when(service.readAll()).thenReturn(dtos);


        mockMvc.perform(get(USERS_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.users.[0].name").value("name1"))
                .andExpect(jsonPath("$._embedded.users.[1].name").value("name2"))
                .andExpect(status().isOk());
    }
}
