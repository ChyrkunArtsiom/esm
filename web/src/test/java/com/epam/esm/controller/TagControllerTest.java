package com.epam.esm.controller;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.util.ErrorMessageManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
class TagControllerTest {
    private final static String TAGS_PATH = "/tags";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TagService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testCreateTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenReturn(tagDTO);

        mockMvc.perform(post(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testingname"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateExistingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        DuplicateTagException ex = new DuplicateTagException(tagDTO.getName(), 40901);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("entityAlreadyExists"), ex.getName());

        mockMvc.perform(post(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isConflict());
    }

    @Test
    void testPutMethod() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenThrow(UnsupportedOperationException.class);

        mockMvc.perform(post(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    void testReadTag() throws Exception {
        TagDTO tagDTO = new TagDTO(1, "testingname");
        Mockito.when(service.read(tagDTO.getId())).thenReturn(tagDTO);

        mockMvc.perform(get(TAGS_PATH + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(tagDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void testReadMissingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(100, "testingname");
        NoTagException ex = new NoTagException(tagDTO.getName(), 40401);
        Mockito.when(service.read(tagDTO.getId())).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("tagDoesntExist"), ex.getName());

        mockMvc.perform(get(TAGS_PATH + "/100"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    void testReadAllTags() throws Exception {
        List<TagDTO> dtos = new ArrayList<>(
                Arrays.asList(new TagDTO(1, "name1"), new TagDTO(2, "name2")));
        Mockito.when(service.readAll()).thenReturn(dtos);


        mockMvc.perform(get(TAGS_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("name1"))
                .andExpect(jsonPath("$.[1].name").value("name2"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.delete(Mockito.any(TagDTO.class))).thenReturn(true);

        mockMvc.perform(delete(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteMissingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.delete(Mockito.any(TagDTO.class))).thenReturn(false);

        mockMvc.perform(delete(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }
}