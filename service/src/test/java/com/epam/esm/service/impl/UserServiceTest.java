package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.RoleDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.RoleMapper;
import com.epam.esm.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO dao;

    @Mock
    private RoleDAO roleDAO;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService service;

    @Test
    public void testCreate() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO user = new UserDTO(1, "user", "password", "Artsiom", "Chyrkun", LocalDate.now().toString(), role);
        Mockito.when(roleDAO.read(Mockito.anyString())).thenReturn(RoleMapper.toEntity(role));
        Mockito.when(dao.create(Mockito.any(User.class))).thenReturn(UserMapper.toEntity(user));
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn(user.getPassword());
        assertEquals(user, service.create(user));
        Mockito.verify(roleDAO, Mockito.times(1)).read(Mockito.anyString());
    }

    @Test
    public void testReadAll() {
        Role role = new Role("ROLE_USER");
        List<User> entities = new ArrayList<>(Arrays.asList(
                new User(1, "name1", "password".toCharArray(),
                        "Ivan", "Ivanov", LocalDate.now(), role),
                new User(2, "name2", "password2".toCharArray(),
                        "Petr", "Petrov", LocalDate.now(), role)));
        Mockito.when(dao.readAll()).thenReturn(entities);

        List<UserViewDTO> users = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        Role role = new Role("ROLE_USER");
        List<User> entities = new ArrayList<>(Arrays.asList(
                new User(1, "name1", "password".toCharArray(),
                        "Ivan", "Ivanov", LocalDate.now(), role),
                new User(2, "name2", "password2".toCharArray(),
                        "Petr", "Petrov", LocalDate.now(), role)));
        Mockito.when(dao.readPaginated(page, size)).thenReturn(entities);

        List<UserViewDTO> users = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(page, size);
        assertEquals(users.size(), size);
    }

    @Test
    public void testRead() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO dto = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        UserViewDTO userViewDTO = new UserViewDTO(1, "tag",
                "Ivan", "Ivanov", LocalDate.now().toString());
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(UserMapper.toEntity(dto));
        UserViewDTO user = service.read(1);
        assertEquals(userViewDTO, user);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
    }

    @Test
    public void testEncode() {
        String password = "password";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode(password);
        System.out.println(encoded);
    }
}
