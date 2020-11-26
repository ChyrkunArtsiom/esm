package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link com.epam.esm.entity.User}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = UserDAO.class)
public class UserService implements AbstractService<UserDTO> {

    private UserDAO dao;

    @Autowired
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public UserDTO create(UserDTO entity) {
        throw new UnsupportedOperationException("User is not supported by create method.");
    }

    @Override
    public UserDTO read(int id) {
        User user = dao.read(id);
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDTO> readAll() {
        List<UserDTO> dtos;
        List<User> entities = dao.readAll();
        dtos = entities.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    /**
     * Gets the list of {@link UserDTO} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link UserDTO} objects
     */
    public List<UserDTO> readPaginated(Integer page, Integer size) {
        List<UserDTO> dtos;
        List<User> entities = dao.readPaginated(page, size);
        dtos = entities.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public UserDTO update(UserDTO entity) {
        throw new UnsupportedOperationException("User is not supported by update method.");
    }

    @Override
    public boolean delete(UserDTO dto) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(Integer size) {
        return dao.getLastPage(size);
    }
}
