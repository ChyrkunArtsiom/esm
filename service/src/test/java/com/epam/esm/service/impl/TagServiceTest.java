package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TagService.class)
class TagServiceTest {

    @Mock
    private TagDAO dao;

    @InjectMocks
    private TagService service;

    @Test
    void testReadAll() {
        List<Tag> entities = new ArrayList<>(
                Arrays.asList(new Tag(1, "name1"), new Tag(2, "name2")));
        Mockito.when(dao.readAll()).thenReturn(entities);

        List<TagDTO> tags = service.readAll();
        assertTrue(tags.size() > 0);
    }

    @Test
    void testCreate() {
        TagDTO dto = new TagDTO(1, "testtag");
        Mockito.when(dao.create(Mockito.any(Tag.class))).thenReturn(TagMapper.toEntity(dto));
        assertEquals(dto, service.create(dto));
    }

    @Test
    void testRead() {
        TagDTO dto = new TagDTO(1, "testtag");
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(TagMapper.toEntity(dto));
        TagDTO tag = service.read(4);
        assertEquals(dto, tag);
    }

    @Test
    void testDelete() {
        TagDTO tag = new TagDTO(1, "tagtodelete");
        Mockito.when(dao.delete(Mockito.any(Tag.class))).thenReturn(true);
        assertTrue(service.delete(tag));
    }
}