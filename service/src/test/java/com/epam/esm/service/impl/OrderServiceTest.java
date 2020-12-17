package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.*;
import com.epam.esm.entity.*;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDAO dao;

    @Mock
    private UserDAO userDAO;

    @Mock
    private GiftCertificateDAO certificateDAO;

    @InjectMocks OrderService service;

    @Test
    void testCreate() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        UserViewDTO userViewDTO = new UserViewDTO(1, "tag",
                "Ivan", "Ivanov", LocalDate.now().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO dto = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        OrderViewDTO viewDTO = new OrderViewDTO(
                1, 10.0, date.toString(), userViewDTO, Arrays.asList(certificate, certificate));
        Order entity = OrderMapper.toEntity(dto);
        Mockito.when(userDAO.read(Mockito.anyInt())).thenReturn(UserMapper.toEntity(userDTO));
        Mockito.when(certificateDAO.read(Mockito.anyInt())).thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(dao.create(Mockito.any(Order.class))).thenReturn(entity);
        assertEquals(viewDTO, service.create(dto));
        Mockito.verify(userDAO, Mockito.times(1)).read(Mockito.anyInt());
        Mockito.verify(certificateDAO, Mockito.times(2)).read(Mockito.anyInt());
    }

    @Test
    void testRead() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        UserViewDTO userViewDTO = new UserViewDTO(1, "tag",
                "Ivan", "Ivanov", LocalDate.now().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO dto = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        OrderViewDTO orderViewDTO = new OrderViewDTO(1, 10.0, date.toString(), userViewDTO, Arrays.asList(certificate, certificate));
        Order entity = OrderMapper.toEntity(dto);
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(entity);
        OrderViewDTO order = service.read(1);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
        assertEquals(orderViewDTO, order);
    }

    @Test
    void readAll() {
        Role role = new Role("ROLE_USER");
        User user = new User(1, "tag", "password".toCharArray(),
                "Ivan", "Ivanov", LocalDate.now(), role);
        GiftCertificate certificate = new GiftCertificate(
                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null);
        List<Order> orders = new ArrayList<>(
                Arrays.asList(new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
        Mockito.when(dao.readAll()).thenReturn(orders);
        List<OrderViewDTO> orderDTOS = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertEquals(orderDTOS.size(), 2);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        Role role = new Role("ROLE_USER");
        User user = new User(1, "tag", "password".toCharArray(),
                "Ivan", "Ivanov", LocalDate.now(), role);
        GiftCertificate certificate = new GiftCertificate(
                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null);
        List<Order> orders = new ArrayList<>(
                Arrays.asList(new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
        Mockito.when(dao.readPaginated(page, size)).thenReturn(orders);

        List<OrderViewDTO> tags = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(page, size);
        assertEquals(tags.size(), size);
    }

    @Test
    public void testUpdate() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(OrderMapper.toEntity(order));
        Mockito.when(userDAO.read(Mockito.anyInt())).thenReturn(UserMapper.toEntity(userDTO));
        Mockito.when(certificateDAO.read(Mockito.anyInt())).thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(dao.update(OrderMapper.toEntity(order))).thenReturn(OrderMapper.toEntity(order));
        assertNull(service.update(order));
        Mockito.verify(userDAO, Mockito.times(1)).read(Mockito.anyInt());
        Mockito.verify(certificateDAO, Mockito.times(2)).read(Mockito.anyInt());
    }

    @Test
    public void testDelete() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Mockito.when(dao.delete(Mockito.any(Order.class))).thenReturn(true);
        assertTrue(service.delete(order));
        Mockito.verify(dao, Mockito.times(1)).delete(Mockito.any(Order.class));
    }

    @Test
    public void testDeleteById() {
        Mockito.when(dao.delete(Mockito.anyInt())).thenReturn(true);
        assertTrue(service.delete(1));
        Mockito.verify(dao, Mockito.times(1)).delete(Mockito.anyInt());
    }

    @Test
    public void testGetMostFrequentTags() {
        List<Tag> entities = new ArrayList<>(
                Arrays.asList(new Tag("name1"), new Tag("name2")));
        Mockito.when(dao.getMostFrequentTags()).thenReturn(entities);
        List<TagDTO> dtos = service.getMostFrequentTags();
        Mockito.verify(dao, Mockito.times(1)).getMostFrequentTags();
        assertEquals(entities.size(), dtos.size());
    }
}