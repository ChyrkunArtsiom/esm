package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = GiftCertificateService.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDAO dao;

    @Mock
    private TagService tagService;

    @InjectMocks
    private GiftCertificateService service;

    @Test
    void testRead() {
        GiftCertificateDTO dto = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity.setCreateDate(OffsetDateTime.now());
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(entity);
        GiftCertificateDTO certificateDTO = service.read(1);
        assertEquals(dto, certificateDTO);
    }

    @Test
    void testReadAll() {
        List<GiftCertificate> entities = new ArrayList<>(
                Arrays.asList(new GiftCertificate(
                        1, "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null),
                        new GiftCertificate(
                                1, "Test certificate", "Description", 1.5, OffsetDateTime.now(), null, 10, null)));
        Mockito.when(dao.readAll()).thenReturn(entities);

        List<GiftCertificateDTO> tags = service.readAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    void testReadByParams() {
        List<GiftCertificate> entities = new ArrayList<>(
                Arrays.asList(new GiftCertificate(
                                1, "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null),
                        new GiftCertificate(
                                1, "Test certificate", "Description", 1.5, OffsetDateTime.now(), null, 10, null)));
        Mockito.when(dao.readByParams(Mockito.any(SearchCriteria.class))).thenReturn(entities);

        SearchCriteria criteria = new SearchCriteria("", "Test certificate", "", "name_asc");
        List<GiftCertificateDTO> tags = service.readByParams(criteria);
        assertTrue(tags.size() > 0);
    }

    @Test
    void testDelete() {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(4, "test", "test",
                BigDecimal.valueOf(1.0), 1, null);
        Mockito.when(dao.delete(Mockito.any(GiftCertificate.class))).thenReturn(true);
        assertTrue(service.delete(certificateDTO));
    }

    @Test
    void testCreate() {
        GiftCertificateDTO dto = new GiftCertificateDTO(4, "test", "test",
                BigDecimal.valueOf(1.0), 1, null);
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity.setCreateDate(OffsetDateTime.now());
        Mockito.when(dao.create(Mockito.any(GiftCertificate.class))).thenReturn(entity);
        assertEquals(dto, service.create(dto));
    }

    @Test
    void testUpdate() {
        List<String> tags = new ArrayList<>(Arrays.asList("rest", "tagtagtest"));
        GiftCertificateDTO certificate = new GiftCertificateDTO(0, "test", "test",
                BigDecimal.valueOf(1.0), 1, tags);
        Mockito.when(dao.read(Mockito.anyString()))
                .thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(dao.update(Mockito.any(GiftCertificate.class)))
                .thenReturn(GiftCertificateMapper.toEntity(certificate));
        assertNull(service.update(certificate));
    }

}