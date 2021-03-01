package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagDAO dao;

    @InjectMocks
    private TagService service;

    @Test
    public void testCreate() {
        TagDTO dto = new TagDTO(1, "testtag");
        Mockito.when(dao.create(Mockito.any(Tag.class))).thenReturn(TagMapper.toEntity(dto));
        assertEquals(dto, service.create(dto));
        Mockito.verify(dao, Mockito.times(1)).create(Mockito.any(Tag.class));
    }

    @Test
    public void testReadAll() {
        List<Tag> entities = new ArrayList<>(
                Arrays.asList(new Tag("name1"), new Tag("name2")));
        Mockito.when(dao.readAll()).thenReturn(entities);

        List<TagDTO> tags = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        List<Tag> entities = new ArrayList<>(
                Arrays.asList(new Tag("name1"), new Tag("name2")));
        Mockito.when(dao.readPaginated(Mockito.any(SearchCriteria.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(entities);

        List<TagDTO> tags = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(Mockito.any(SearchCriteria.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        assertEquals(tags.size(), size);
    }

    @Test void readByParams() {
        int page = 1;
        int size = 2;
        List<Tag> entities = new ArrayList<>(
                Arrays.asList(new Tag("name1"), new Tag("name2")));
        Mockito.when(dao.readPaginated(Mockito.any(SearchCriteria.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(entities);
        Mockito.when(dao.getLastPage(Mockito.any(SearchCriteria.class), Mockito.anyInt())).thenReturn(1);

        SearchCriteria searchCriteria = new SearchCriteria(null, "name", null, null);
        List<TagDTO> tags = service.readByParams(searchCriteria, page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(Mockito.any(SearchCriteria.class), Mockito.any(Integer.class), Mockito.any(Integer.class));
        assertEquals(tags.size(), size);
    }

    @Test
    public void testRead() {
        TagDTO dto = new TagDTO(1, "testtag");
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(TagMapper.toEntity(dto));
        TagDTO tag = service.read(1);
        assertEquals(dto, tag);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
    }

    @Test
    public void testDelete() {
        TagDTO tag = new TagDTO(1, "tagtodelete");
        Mockito.when(dao.delete(Mockito.any(Tag.class))).thenReturn(true);
        assertTrue(service.delete(tag));
        Mockito.verify(dao, Mockito.times(1)).delete(Mockito.any(Tag.class));
    }
}