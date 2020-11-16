package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DuplicateTagException;
import com.epam.esm.exception.NoTagException;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.util.ErrorMessageManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = TagController.class)
@AutoConfigureMockMvc
class TagControllerTest {
    private final static String TAGS_PATH = "/tags";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService service;

    @Test
    public void testCreateTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenReturn(tagDTO);

        mockMvc.perform(post(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value("testingname"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateExistingTag() throws Exception {
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
    public void testPutMethod() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.create(Mockito.any(TagDTO.class))).thenThrow(UnsupportedOperationException.class);

        mockMvc.perform(post(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void testReadTag() throws Exception {
        TagDTO tagDTO = new TagDTO(1, "testingname");
        Mockito.when(service.read(tagDTO.getId())).thenReturn(tagDTO);

        mockMvc.perform(get(TAGS_PATH + "/1"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value(tagDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadMissingTag() throws Exception {
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
    public void testReadAllTags() throws Exception {
        List<TagDTO> dtos = new ArrayList<>(
                Arrays.asList(new TagDTO(1, "name1"), new TagDTO(2, "name2")));
        Mockito.when(service.readAll()).thenReturn(dtos);


        mockMvc.perform(get(TAGS_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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

        mockMvc.perform(delete(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMissingTag() throws Exception {
        TagDTO tagDTO = new TagDTO(0, "testingname");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(tagDTO);
        Mockito.when(service.delete(Mockito.any(TagDTO.class))).thenReturn(false);

        mockMvc.perform(delete(TAGS_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }
}