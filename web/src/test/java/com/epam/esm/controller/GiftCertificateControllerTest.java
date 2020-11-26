package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;
import com.epam.esm.service.impl.GiftCertificateService;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.util.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GiftCertificateController.class)
@AutoConfigureMockMvc
class GiftCertificateControllerTest {
    private final static String CERTIFICATES_PATH = "/certificates";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiftCertificateService service;

    @Test
    public void testPostCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.55), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(certificateDTO);
        Mockito.when(service.create(Mockito.any(GiftCertificateDTO.class))).thenReturn(certificateDTO);

        mockMvc.perform(post(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value("Test certificate"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostExistingCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(certificateDTO);
        DuplicateCertificateException ex = new DuplicateCertificateException(certificateDTO.getName());
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
    public void testGetCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        Mockito.when(service.read(certificateDTO.getId())).thenReturn(certificateDTO);

        mockMvc.perform(get(CERTIFICATES_PATH + "/1"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name").value(certificateDTO.getName()))
                .andExpect(jsonPath("$.description").value(certificateDTO.getDescription()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMissingCertificate() throws Exception {
        String id = "1";
        NoCertificateException ex = new NoCertificateException(id);
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("certificateDoesntExist"), ex.getName());

        mockMvc.perform(get(CERTIFICATES_PATH + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCertificatesByParams() throws Exception {
        List<GiftCertificateDTO> dtos = new ArrayList<>(
                Arrays.asList(new GiftCertificateDTO(
                                1, "Test certificate1", "Description", BigDecimal.valueOf(1.5), 10, null),
                        new GiftCertificateDTO(
                                2, "Test certificate2", "Description", BigDecimal.valueOf(1.5), 10, null)
                ));
        Mockito.when(service.readWithParams(
                Mockito.any(SearchCriteria.class),
                Mockito.nullable(Integer.class),
                Mockito.nullable(Integer.class)))
                .thenReturn(dtos);

        mockMvc.perform(get(CERTIFICATES_PATH))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.certificates.[0].name")
                        .value("Test certificate1"))
                .andExpect(jsonPath("$._embedded.certificates.[1].name")
                        .value("Test certificate2"))
                .andExpect(status().isOk());

        Mockito.verify(service,
                Mockito.times(1))
                .readWithParams(
                        Mockito.any(SearchCriteria.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class));
    }

    @Test
    public void testPutCertificate() throws Exception {
        TagDTO one = new TagDTO(1, "tagOne");
        TagDTO two = new TagDTO(2, "tagTwo");
        Set<TagDTO> tags = new HashSet<>(Arrays.asList(one, two));
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, tags);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.update(Mockito.any(GiftCertificateDTO.class))).thenReturn(null);

        mockMvc.perform(put(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCertificate() throws Exception {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.delete(Mockito.any(GiftCertificateDTO.class))).thenReturn(true);

        mockMvc.perform(delete(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMissingCertificate() throws Exception {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(giftCertificateDTO);
        Mockito.when(service.delete(Mockito.any(GiftCertificateDTO.class))).thenReturn(false);

        mockMvc.perform(delete(CERTIFICATES_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCertificateByUrlId() throws Exception {
        Mockito.when(service.delete(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete(CERTIFICATES_PATH + "/1"))
                .andExpect(status().isNoContent());
    }
}