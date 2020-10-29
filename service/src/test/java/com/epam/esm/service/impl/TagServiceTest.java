package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.AbstractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TagService.class)
class TagServiceTest {

    @Autowired
    private TagService service;

    @Autowired
    private TagDAO dao;

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
        TagDTO dto = new TagDTO(0, "testtag3");
        assertTrue(service.create(dto) != null);
    }

    @Test
    void testRead() {
        TagDTO tag = (TagDTO)service.read(4);
        assertFalse(tag.getName().isEmpty());
    }

    @Test
    void testDelete() {
        TagDTO tag = new TagDTO(4, "delete");
        assertTrue(service.delete(tag));
    }
}