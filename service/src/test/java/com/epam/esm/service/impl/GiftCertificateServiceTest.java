package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDAO dao;

    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private GiftCertificateService service;

    @Test
    public void testCreate() {
        TagDTO firstTag = new TagDTO(1, "firsttag");
        TagDTO secondTag = new TagDTO(2, "secondtag");
        Set<TagDTO> tags = new HashSet<>(Arrays.asList(firstTag, secondTag));
        GiftCertificateDTO dto = new GiftCertificateDTO(4, "test", "test",
                BigDecimal.valueOf(1.0), 1, tags);
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity.setCreateDate(OffsetDateTime.now());
        Mockito.when(tagDAO.read(firstTag.getName())).thenReturn(TagMapper.toEntity(firstTag));
        Mockito.when(tagDAO.read(secondTag.getName())).thenReturn(TagMapper.toEntity(secondTag));
        Mockito.when(dao.create(Mockito.any(GiftCertificate.class))).thenReturn(entity);
        assertEquals(dto, service.create(dto));
        Mockito.verify(tagDAO, Mockito.times(2)).read(Mockito.anyString());
        Mockito.verify(dao, Mockito.times(1)).create(Mockito.any(GiftCertificate.class));
    }

    @Test
    public void testRead() {
        GiftCertificateDTO dto = new GiftCertificateDTO(
                1, "Test certificate", "Description", BigDecimal.valueOf(1.5), 10, null);
        GiftCertificate entity = GiftCertificateMapper.toEntity(dto);
        entity.setCreateDate(OffsetDateTime.now());
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(entity);
        GiftCertificateDTO certificateDTO = service.read(1);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
        assertEquals(dto, certificateDTO);
    }

    @Test
    public void testReadAll() {
        List<GiftCertificate> entities = new ArrayList<>(
                Arrays.asList(new GiftCertificate(
                        "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null),
                        new GiftCertificate(
                                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null, 10, null)));
        Mockito.when(dao.readAll()).thenReturn(entities);
        List<GiftCertificateDTO> services = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertTrue(services.size() > 0);
    }

    @Test
    public void testReadByParams() {
        List<GiftCertificate> entities = new ArrayList<>(
                Arrays.asList(new GiftCertificate(
                                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null),
                        new GiftCertificate(
                                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null, 10, null)));
        Mockito.when(dao.readByParams(
                Mockito.any(SearchCriteria.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(entities);
        Mockito.when(dao.getLastPage(Mockito.any(SearchCriteria.class), Mockito.anyInt())).thenReturn(2);

        SearchCriteria criteria = new SearchCriteria("", "Test certificate", "", "name_asc");
        List<GiftCertificateDTO> services = service.readByParams(criteria, 1, 1);
        Mockito.verify(dao, Mockito.times(1))
                .readByParams(Mockito.any(SearchCriteria.class), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(dao, Mockito.times(1))
                .getLastPage(Mockito.any(SearchCriteria.class), Mockito.anyInt());
        assertTrue(services.size() > 0);
    }

    @Test
    public void testUpdate() {
        TagDTO rest = new TagDTO(1, "rest");
        TagDTO testTag = new TagDTO(2, "tagtagtest");
        Set<TagDTO> tags = new HashSet<>(Arrays.asList(rest, testTag));
        GiftCertificateDTO certificate = new GiftCertificateDTO(0, "test", "test",
                BigDecimal.valueOf(1.0), 1, tags);
        Mockito.when(dao.read(Mockito.anyString()))
                .thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(dao.update(Mockito.any(GiftCertificate.class)))
                .thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(tagDAO.read(rest.getName())).thenReturn(TagMapper.toEntity(rest));
        Mockito.when(tagDAO.read(testTag.getName())).thenReturn(TagMapper.toEntity(testTag));
        assertNull(service.update(certificate));
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyString());
        Mockito.verify(tagDAO, Mockito.times(2)).read(Mockito.anyString());
        Mockito.verify(dao, Mockito.times(1)).update(Mockito.any(GiftCertificate.class));
    }

    @Test
    public void testDelete() {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO(4, "test", "test",
                BigDecimal.valueOf(1.0), 1, null);
        Mockito.when(dao.delete(Mockito.any(GiftCertificate.class))).thenReturn(true);
        assertTrue(service.delete(certificateDTO));
        Mockito.verify(dao, Mockito.times(1)).delete(Mockito.any(GiftCertificate.class));
    }

    @Test
    public void testDeleteById() {
        Mockito.when(dao.delete(Mockito.anyInt())).thenReturn(true);
        assertTrue(service.delete(1));
        Mockito.verify(dao, Mockito.times(1)).delete(Mockito.anyInt());
    }
}