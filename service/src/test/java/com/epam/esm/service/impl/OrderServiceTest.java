package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.OrderMapper;
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

    @InjectMocks OrderService service;

    @Test
    void testRead() {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO dto = new OrderDTO(1, 10, date.toString(), userDTO, Arrays.asList(certificate, certificate));
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
                Arrays.asList(new Order(10, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
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
                Arrays.asList(new Order(10, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate)),
                        new Order(10, OffsetDateTime.now(), user, Arrays.asList(certificate, certificate))));
        Mockito.when(dao.readPaginated(page, size)).thenReturn(orders);

        List<OrderDTO> tags = service.readPaginated(page, size);
        Mockito.verify(dao, Mockito.times(1)).readPaginated(page, size);
        assertEquals(tags.size(), size);
    }
}