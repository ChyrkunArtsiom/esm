package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ArgumentIsNotPresent;
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
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO dto = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Order entity = OrderMapper.toEntity(dto);
        Mockito.when(userDAO.read(Mockito.anyInt())).thenReturn(UserMapper.toEntity(userDTO));
        Mockito.when(certificateDAO.read(Mockito.anyInt())).thenReturn(GiftCertificateMapper.toEntity(certificate));
        Mockito.when(dao.create(Mockito.any(Order.class))).thenReturn(entity);
        assertEquals(dto, service.create(dto));
        Mockito.verify(userDAO, Mockito.times(1)).read(Mockito.anyInt());
        Mockito.verify(certificateDAO, Mockito.times(2)).read(Mockito.anyInt());
    }

    @Test
    void testRead() {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO dto = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Order entity = OrderMapper.toEntity(dto);
        Mockito.when(dao.read(Mockito.anyInt())).thenReturn(entity);
        OrderDTO order = service.read(1);
        Mockito.verify(dao, Mockito.times(1)).read(Mockito.anyInt());
        assertEquals(dto, order);
    }

    @Test
    void readAll() {
        User user = new User(1, "user", "password".toCharArray());
        GiftCertificate certificate = new GiftCertificate(
                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null);
        List<Order> orders = new ArrayList<>(
                Arrays.asList(new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
        Mockito.when(dao.readAll()).thenReturn(orders);
        List<OrderDTO> orderDTOS = service.readAll();
        Mockito.verify(dao, Mockito.times(1)).readAll();
        assertEquals(orderDTOS.size(), 2);
    }

    @Test
    public void testReadPaginated() {
        int page = 1;
        int size = 2;
        User user = new User(1, "user", "password".toCharArray());
        GiftCertificate certificate = new GiftCertificate(
                "Test certificate", "Description", 1.5, OffsetDateTime.now(), null,  10, null);
        List<Order> orders = new ArrayList<>(
                Arrays.asList(new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10.0, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
        Mockito.when(dao.readPaginated(page, size)).thenReturn(orders);

        List<OrderDTO> tags = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(page, size);
        assertEquals(tags.size(), size);
    }

    @Test
    public void testUpdate() {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
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
    public void testUpdateWhenIdNotPresent() {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(null, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        assertThrows(ArgumentIsNotPresent.class,
                () -> service.update(order));
    }

    @Test
    public void testDelete() {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
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