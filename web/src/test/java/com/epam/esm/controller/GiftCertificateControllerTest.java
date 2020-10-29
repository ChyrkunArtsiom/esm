package com.epam.esm.controller;

import com.epam.esm.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
class GiftCertificateControllerTest {

    @Autowired
    private WebApplicationContext context;

    private final static String CERTIFICATES_PATH = "/certificates/";

    @Test
    void testReadCertificate() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get(CERTIFICATES_PATH + 1)).andExpect(status().isOk());
    }

    @Test
    void testReadMissingCertificate() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get(CERTIFICATES_PATH + 100)).andExpect(status().isNotFound());
    }
}