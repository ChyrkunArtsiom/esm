package com.epam.esm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GiftCertificateController.class)
class GiftCertificateControllerTest {

    private GiftCertificateController controller;

    private final static String CERTIFICATES_PATH = "/certificates/";

    @Autowired
    public void setController(GiftCertificateController controller) {
        this.controller = controller;
    }

    @Test
    void testReadCertificate() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get(CERTIFICATES_PATH + 1)).andExpect(status().isOk());
    }
}