package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
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
public class UserServiceTest {

    @Mock
    private UserDAO dao;

    @InjectMocks
    private UserService service;

    @Test
    public void testReadAll() {
        List<User> entities = new ArrayList<>(Arrays.asList(
                new User(1, "name1", "password1".toCharArray()),
                new User(2, "name2", "password2".toCharArray())));
        Mockito.when(dao.readAll()).thenReturn(entities);

        List<UserDTO> users = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        List<User> entities = new ArrayList<>(Arrays.asList(
                new User(1, "name1", "password1".toCharArray()),
                new User(2, "name2", "password2".toCharArray())));
        Mockito.when(dao.readPaginated(page, size)).thenReturn(entities);

        List<UserDTO> users = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(page, size);
        assertEquals(users.size(), size);
    }

    @Test
    public void testRead() {
        UserDTO dto = new UserDTO(1, "name", "password".toCharArray());
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(UserMapper.toEntity(dto));
        UserDTO user = service.read(1);
        assertEquals(dto, user);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
    }
}
