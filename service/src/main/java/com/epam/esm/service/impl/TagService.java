package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link Tag}. Implements {@link AbstractService}.
 */
@Service
/*@ComponentScan(basePackageClasses = TagDAO.class)*/
public class TagService implements AbstractService<TagDTO, TagDTO> {

    private TagDAO dao;

    /**
     * Sets {@link TagDAO} object.
     *
     * @param dao the {@link TagDAO} object
     */
    @Autowired
    public void setDao(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO dto) {
        Tag entity = TagMapper.toEntity(dto);
        entity = dao.create(entity);
        return TagMapper.toDto(entity);
    }

    @Override
    public TagDTO read(int id) {
        Tag tag = dao.read(id);
        return TagMapper.toDto(tag);
    }

    /**
     * Gets {@link TagDTO} object by the name.
     *
     * @param name the name string
     * @return the {@link TagDTO} object
     */
    public TagDTO read(String name) {
        Tag tag = dao.read(name);
        return TagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> readAll() {
        List<TagDTO> dtos;
        List<Tag> entities = dao.readAll();
        dtos = entities.stream().map(TagMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<TagDTO> readPaginated(Integer page, Integer size) {
        List<TagDTO> dtos;
        List<Tag> entities = dao.readPaginated(new SearchCriteria("", "", "", "name_asc"), page, size);
        dtos = entities.stream().map(TagMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    /**
     * Gets the list of {@link TagDTO} objects by parameters.
     * They are the fields of {@link SearchCriteria} class.
     *
     * @param searchCriteria the {@link SearchCriteria} object
     * @return the list of {@link TagDTO} objects
     */
    public List<TagDTO> readByParams(SearchCriteria searchCriteria, Integer page, Integer size) {
        List<TagDTO> dtos;
        List<Tag> tags;
        if (page == null) {
            tags = dao.readPaginated(searchCriteria, null, null);
        } else {
            int lastPage = getLastPage(searchCriteria, size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            tags = dao.readPaginated(searchCriteria, page, size);
        }
        dtos = tags.stream().map(TagMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Tag is not supported by update method.");
    }

    @Override
    @Transactional
    public boolean delete(TagDTO dto) {
        Tag entity = TagMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return dao.delete(id);
    }

    @Override
    public int getLastPage(Integer size) {
        return dao.getLastPage(new SearchCriteria("", "", "", "name_asc"), size);
    }

    /**
     * Gets a number of the last page of objects.
     *
     * @param searchCriteria the {@link SearchCriteria} object which holds searching parameters
     * @param size the size of page
     * @return the number of the last page
     */
    public int getLastPage(SearchCriteria searchCriteria, Integer size) {
        return dao.getLastPage(searchCriteria, size);
    }
}
