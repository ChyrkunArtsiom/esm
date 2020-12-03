package com.epam.esm.controller.unit_tests;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.*;
import com.epam.esm.exception.NoOrderException;
import com.epam.esm.service.impl.OrderService;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.ErrorMessageManager;
import com.epam.esm.util.jwt.JwtUtilImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {
    private final static String ORDERS_PATH = "/orders";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtilImpl jwtUtil;

    private static AuthenticationUser user;
    private static String jwt;

    @BeforeAll
    public static void setUp() {
        RoleDTO role = new RoleDTO(1, "ROLE_ADMIN");
        UserDTO userDTO = new UserDTO(1, "admin", "password", "Artsiom", "Chyrkun", "1994-06-18", role);
        user = new AuthenticationUser(userDTO);
        jwt = "Bearer test";
    }

    @BeforeEach
    public void setUpAuthorizationMock() {
        Mockito.when(jwtUtil.getUsernameFromToken(Mockito.anyString())).thenReturn(user.getUsername());
        Mockito.when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(jwtUtil.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);
    }

    @Test
    public void testPostOrder() throws Exception {
        UserViewDTO userDTO = new UserViewDTO(1, "user",
                "Ivan", "Ivanov", LocalDate.now().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderViewDTO order = new OrderViewDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(order);
        Mockito.when(service.create(Mockito.any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post(ORDERS_PATH).contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.user.name").value("user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrder() throws Exception {
        UserViewDTO userDTO = new UserViewDTO(1, "tag",
                "Ivan", "Ivanov", LocalDate.now().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderViewDTO order = new OrderViewDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        Mockito.when(service.read(userDTO.getId())).thenReturn(order);

        mockMvc.perform(get(ORDERS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.user.name").value(userDTO.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMissingOrder() throws Exception {
        String id = "1";
        NoOrderException ex = new NoOrderException("1");
        Mockito.when(service.read(Integer.valueOf(id))).thenThrow(ex);
        String locale = "en_US";
        ErrorMessageManager manager = ErrorMessageManager.valueOf(locale);
        String errorMessage = String.format(manager.getMessage("orderDoesntExist"), id);

        mockMvc.perform(get(ORDERS_PATH + "/1").header("Authorization", jwt))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetAllOrders() throws Exception {
        UserViewDTO userDTO = new UserViewDTO(1, "user",
                "Ivan", "Ivanov", LocalDate.now().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderViewDTO order = new OrderViewDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        List<OrderViewDTO> orders = Arrays.asList(order, order);
        Mockito.when(service.readAll()).thenReturn(orders);


        mockMvc.perform(get(ORDERS_PATH).header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.orders.[0].user.name").value("user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutOrder() throws Exception {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "user", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(order);
        Mockito.when(service.update(Mockito.any(OrderDTO.class))).thenReturn(null);

        mockMvc.perform(put(ORDERS_PATH).contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(order);
        Mockito.when(service.delete(Mockito.any(OrderDTO.class))).thenReturn(true);

        mockMvc.perform(delete(ORDERS_PATH).contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMissingOrder() throws Exception {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO userDTO = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        GiftCertificateDTO certificate = new GiftCertificateDTO(
                1, "certificate", "description", BigDecimal.valueOf(100.0),
                date.toString(), date.toString(), 10, null);
        OrderDTO order = new OrderDTO(1, 10.0, date.toString(), userDTO, Arrays.asList(certificate, certificate));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(order);
        Mockito.when(service.delete(Mockito.any(OrderDTO.class))).thenReturn(false);

        mockMvc.perform(delete(ORDERS_PATH).contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", jwt))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrderByUrlId() throws Exception {
        Mockito.when(service.delete(Mockito.anyInt())).thenReturn(true);
        mockMvc.perform(delete(ORDERS_PATH + "/1").header("Authorization", jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetMostFrequentTags() throws Exception{
        List<TagDTO> dtos = new ArrayList<>(
                Arrays.asList(new TagDTO(1, "name1"), new TagDTO(2, "name2")));
        Mockito.when(service.getMostFrequentTags()).thenReturn(dtos);


        mockMvc.perform(get(ORDERS_PATH + "/most_frequent_tag").header("Authorization", jwt))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.tags.[0].name").value("name1"))
                .andExpect(jsonPath("$._embedded.tags.[1].name").value("name2"))
                .andExpect(status().isOk());
    }

}
