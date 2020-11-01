package com.epam.esm.controller;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.GiftCertificateService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
class GiftCertificateControllerTest {
    private final static String CERTIFICATES_PATH = "/certificates";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AbstractService<GiftCertificate, GiftCertificateDTO> service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testCreateCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.55), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(certificateDTO);
        Mockito.when(service.create(Mockito.any(GiftCertificateDTO.class))).thenReturn(certificateDTO);

        mockMvc.perform(post(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test certificate"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateExistingCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(certificateDTO);
        DuplicateCertificateException ex = new DuplicateCertificateException(certificateDTO.getName(), 40902);
        Mockito.when(service.create(Mockito.any(GiftCertificateDTO.class))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("entityAlreadyExists"), ex.getName());

        mockMvc.perform(post(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isConflict());
    }

    @Test
    void testReadCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        Mockito.when(service.read(certificateDTO.getId())).thenReturn(certificateDTO);

        mockMvc.perform(get(CERTIFICATES_PATH + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(certificateDTO.getName()))
                .andExpect(jsonPath("$.description").value(certificateDTO.getDescription()))
                .andExpect(status().isOk());
    }

    @Test
    void testReadMissingCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                100, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        NoCertificateException ex = new NoCertificateException(certificateDTO.getName(), 40402);
        Mockito.when(service.read(certificateDTO.getId())).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("certificateDoesntExist"), ex.getName());

        mockMvc.perform(get(CERTIFICATES_PATH + "/100"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());
    }

    @Test
    void testReadAllCertificates() throws Exception {
        List<GiftCertificateDTO> dtos = new ArrayList<>(
                Arrays.asList(new GiftCertificateDTO(
                        100, "Test certificate1", "Description", BigDecimal.valueOf(1.5), 10, null),
                        new GiftCertificateDTO(
                        100, "Test certificate2", "Description", BigDecimal.valueOf(1.5), 10, null)
                ));
        Mockito.when(service.readAll()).thenReturn(dtos);


        mockMvc.perform(get(CERTIFICATES_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("Test certificate1"))
                .andExpect(jsonPath("$.[1].name").value("Test certificate2"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCertificate() throws Exception {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.delete(Mockito.any(GiftCertificateDTO.class))).thenReturn(true);

        mockMvc.perform(delete(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteMissingCertificate() throws Exception {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.delete(Mockito.any(GiftCertificateDTO.class))).thenReturn(false);

        mockMvc.perform(delete(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCertificate() throws Exception {
        List<String> tags = new ArrayList<>(Arrays.asList("tagOne", "tagTwo"));
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, tags);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.update(Mockito.any(GiftCertificateDTO.class))).thenReturn(null);

        mockMvc.perform(put(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }
}