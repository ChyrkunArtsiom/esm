package com.epam.esm.controller.unit_tests;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.util.SearchCriteria;
import com.epam.esm.util.jwt.JwtUtilImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TagController.class)
@AutoConfigureMockMvc
class TagControllerTest {
    private final static String TAGS_PATH = "/tags";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService service;

    @MockBean
    private UserService userService;

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
        Mockito.when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(jwtUtil.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);
    }

    @Test
    public void testPostTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenReturn(tagDTO);

        mockMvc.perform(post(TAGS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value("testingname"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostExistingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        DuplicateTagException ex = new DuplicateTagException(tagDTO.getName());
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("entityAlreadyExists"), ex.getName());

        mockMvc.perform(post(TAGS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Authorization", jwt))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPutTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);

        mockMvc.perform(put(TAGS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", jwt))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void testGetTag() throws Exception {
        TagDTO tagDTO = new TagDTO(1, "testingname");
        Mockito.when(service.read(tagDTO.getId())).thenReturn(tagDTO);

        mockMvc.perform(get(TAGS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value(tagDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMissingTag() throws Exception {
        String id = "1";
        NoTagException ex = new NoTagException("1");
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("tagDoesntExist"), id);

        mockMvc.perform(get(TAGS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetTagsByParams() throws Exception {
        List<TagDTO> dtos = new ArrayList<>(
                Arrays.asList(new TagDTO(1, "name1"), new TagDTO(2, "name2")));
        Mockito.when(service.readByParams(
                Mockito.any(SearchCriteria.class),
                Mockito.nullable(Integer.class),
                Mockito.nullable(Integer.class)))
                .thenReturn(dtos);


        mockMvc.perform(get(TAGS_PATH).header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.tags.[0].name").value("name1"))
                .andExpect(jsonPath("$._embedded.tags.[1].name").value("name2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.delete(Mockito.any(TagDTO.class))).thenReturn(true);

        mockMvc.perform(delete(TAGS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTagByUrlId() throws Exception {
        Mockito.when(service.delete(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete(TAGS_PATH + "/1").header("Authorization", jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMissingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.delete(Mockito.any(TagDTO.class))).thenReturn(false);

        mockMvc.perform(delete(TAGS_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", jwt))
                .andExpect(status().isNotFound());
    }
}