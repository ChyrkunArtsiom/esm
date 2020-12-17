package com.epam.esm.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDTOTest {

    private static OrderDTO order;

    @BeforeAll
    static void setUp() {
        RoleDTO role = new RoleDTO(1, "ROLE_USER");
        UserDTO user = new UserDTO(1, "tag", "password",
                "Ivan", "Ivanov", LocalDate.now().toString(), role);
        GiftCertificateDTO certificate = new GiftCertificateDTO(1,
                "certificate", "description", BigDecimal.valueOf(100.0), LocalDate.now().toString(),
                LocalDate.now().toString(), 10, null);
        order = new OrderDTO(1, 10.0, LocalDate.now().toString(), user,
                Arrays.asList(certificate, certificate));

    }

    @Test
    public void testToString() {
        String expected = String.format("Order: {id: %d, cost: %f, purchase date: %s, user: %s, certificates: %s, %s.",
                order.getId(), order.getCost(), order.getPurchaseDate(), order.getUser(),
                order.getCertificates().get(0),
                order.getCertificates().get(1));
        assertEquals(expected, order.toString());
    }
}
