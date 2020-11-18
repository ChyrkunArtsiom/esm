package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NoOrderException;
import com.epam.esm.service.impl.OrderService;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.ErrorMessageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {
    private final static String ORDERS_PAHT = "/orders";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @Test
    public void testRead() throws Exception {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Mockito.when(service.read(userDTO.getId())).thenReturn(order);

        mockMvc.perform(get(ORDERS_PAHT + "/1"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.user.name").value(userDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadMissing() throws Exception {
        String id = "1";
        NoOrderException ex = new NoOrderException("1", 40404);
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("orderDoesntExist"), id);

        mockMvc.perform(get(ORDERS_PAHT + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testReadAll() throws Exception {
        UserDTO userDTO = new UserDTO(1, "user", "password".toCharArray());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        List<OrderDTO> orders = Arrays.asList(order, order);
        Mockito.when(service.readAll()).thenReturn(orders);


        mockMvc.perform(get(ORDERS_PAHT))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.orders.[0].user.name").value("user"))
                .andExpect(status().isOk());
    }

}
