package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TagController.class)
class TagControllerTest {

    @Autowired
    private TagController controller;

    @Test
    void testRead() throws Exception {
        TagDTO expectedTag = new TagDTO(1, "rest");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/tags/tag/1")).
                andExpect(view().name("tags/tag")).
                andExpect(model().attributeExists("tag")).
                andExpect(model().attribute("tag", expectedTag));
    }

    @Test
    void testReadForMissing() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/tags/tag/100")).
                andExpect(view().name("error/404"));
    }
}